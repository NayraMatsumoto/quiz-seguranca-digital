# Script para criar Postgres no Render, anexar ao serviço e disparar deploy
param(
    [string]$ApiKey = 'rnd_ZpLKKc3GvUICDPngnxQcjHouANr3'
)
Write-Host "Using Render API key from param (hidden)"
$headers = @{ Authorization = "Bearer $ApiKey"; Accept = 'application/json' }

Write-Host "Listing services to find quiz-seguranca-digital..."
$services = Invoke-RestMethod -Headers $headers -Uri 'https://api.render.com/v1/services?limit=100'
# API returns items with shape { cursor, service }
$svcList = $services | ForEach-Object { $_.service }
$target = $svcList | Where-Object { ($_.repo -and ($_.repo -like '*quiz-seguranca-digital*')) -or ($_.name -and ($_.name -eq 'quiz-seguranca-digital')) } | Select-Object -First 1
if ($null -eq $target) {
    Write-Host "Service not found. Available services:"
    $services | Select-Object id,name,repo | Format-Table -AutoSize
    exit 1
}

# Found service info
$ownerId = $target.ownerId
$serviceId = $target.id
Write-Host "Found service:`t$($target.name)";
Write-Host "serviceId:`t$serviceId";
Write-Host "ownerId:`t$ownerId";

# Create Postgres (do not print sensitive fields)
$createBody = @{ name = 'quiz-db'; plan = 'free'; ownerId = $ownerId; version = '15'; region = 'oregon' } | ConvertTo-Json
Write-Host "Creating Postgres (free)..."
$create = Invoke-RestMethod -Method Post -Uri 'https://api.render.com/v1/postgres' -Headers $headers -Body $createBody -ContentType 'application/json'
$createId = $create.id
Write-Host "Postgres id: $createId"
if (-not $createId) { Write-Host "Failed to create Postgres."; exit 2 }

# Poll for availability
Write-Host "Polling DB status until 'available' (timeout ~10 minutes)..."
$status = $null
for ($i=0; $i -lt 120; $i++) {
    Start-Sleep -Seconds 5
    $status = Invoke-RestMethod -Headers $headers -Uri "https://api.render.com/v1/postgres/$createId"
    Write-Host ("Attempt {0}: status={1}" -f ($i+1), $status.status)
    if ($status.status -eq 'available') { break }
    if ($status.status -match 'failed|error') { break }
}

if ($status.status -ne 'available') {
    Write-Host "DB did not become available in time. Current status: $($status.status)"
    exit 3
}

# Extract connection string
$external = $null
if ($status.externalConnectionString) { $external = $status.externalConnectionString }
elseif ($status.postgresConnectionInfo -and $status.postgresConnectionInfo.externalConnectionString) { $external = $status.postgresConnectionInfo.externalConnectionString }

if (-not $external) { Write-Host "Could not extract external connection string from API response."; exit 4 }

# Attach DATABASE_URL and SPRING_PROFILES_ACTIVE to service (do not echo secrets)
Write-Host "DB available. Attaching DATABASE_URL and SPRING_PROFILES_ACTIVE=prod to service (secrets will not be printed)."
$patchBody = @{ envVars = @(@{ key = 'DATABASE_URL'; value = $external }, @{ key = 'SPRING_PROFILES_ACTIVE'; value = 'prod' }) } | ConvertTo-Json -Depth 5
$patched = Invoke-RestMethod -Method Patch -Uri "https://api.render.com/v1/services/$serviceId" -Headers $headers -Body $patchBody -ContentType 'application/json'
Write-Host "Patched service. Env vars set on service id: $serviceId"

# Trigger deploy
Write-Host "Triggering deploy..."
$deploy = Invoke-RestMethod -Method Post -Uri "https://api.render.com/v1/services/$serviceId/deploys" -Headers $headers -Body '{}' -ContentType 'application/json'
Write-Host "Deploy triggered (check Render Dashboard for progress)."

Write-Host "Script finished."