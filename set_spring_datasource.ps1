param(
    [string]$ApiKey,
    [string]$ServiceId,
    [string]$JdbcUrl,
    [string]$DbUser,
    [string]$DbPass
)
$headers = @{ Authorization = "Bearer $ApiKey"; Accept = 'application/json' }
$body = @{ envVars = @(
    @{ key = 'SPRING_DATASOURCE_URL'; value = $JdbcUrl },
    @{ key = 'SPRING_DATASOURCE_USERNAME'; value = $DbUser },
    @{ key = 'SPRING_DATASOURCE_PASSWORD'; value = $DbPass },
    @{ key = 'SPRING_PROFILES_ACTIVE'; value = 'prod' }
) }
Write-Host "Patching service with Spring datasource env vars..."
$patched = Invoke-RestMethod -Method Patch -Uri "https://api.render.com/v1/services/$ServiceId" -Headers $headers -Body ($body | ConvertTo-Json -Depth 5) -ContentType 'application/json'
Write-Host "Patch response received. Triggering deploy..."
$deploy = Invoke-RestMethod -Method Post -Uri "https://api.render.com/v1/services/$ServiceId/deploys" -Headers $headers -Body '{}' -ContentType 'application/json'
Write-Host "Deploy triggered: $($deploy.id) status: $($deploy.status)"
