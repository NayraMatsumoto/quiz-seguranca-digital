param(
    [string]$ApiKey,
    [string]$ServiceId,
    [string]$DatabaseUrl
)
$headers = @{ Authorization = "Bearer $ApiKey"; Accept = 'application/json' }
$body = @{ envVars = @(@{ key = 'DATABASE_URL'; value = $DatabaseUrl }, @{ key = 'SPRING_PROFILES_ACTIVE'; value = 'prod' }) }
Write-Host "Patching service $ServiceId..."
$patched = Invoke-RestMethod -Method Patch -Uri "https://api.render.com/v1/services/$ServiceId" -Headers $headers -Body ($body | ConvertTo-Json -Depth 5) -ContentType 'application/json'
Write-Host "Patch result:"; $patched | ConvertTo-Json

Write-Host "Triggering deploy for service $ServiceId..."
$deploy = Invoke-RestMethod -Method Post -Uri "https://api.render.com/v1/services/$ServiceId/deploys" -Headers $headers -Body '{}' -ContentType 'application/json'
Write-Host "Deploy result:"; $deploy | ConvertTo-Json

# Show service envVars
Write-Host "Fetching service details..."
$service = Invoke-RestMethod -Uri "https://api.render.com/v1/services/$ServiceId" -Headers $headers
Write-Host "Service envVars:"; ($service.serviceDetails.envVars | ConvertTo-Json)