param(
    [string]$ApiKey = 'rnd_jb62To4XgKZkxwYiJqkOQqxwrGbf',
    [string]$ServiceId = 'srv-d8jln36gvqtc73egp9s0'
)
$headers = @{ Authorization = "Bearer $ApiKey"; Accept = 'application/json' }
$Vars = @{
    'DATABASE_URL' = 'postgresql://quiz_db_prwg_user:0Zw4VDgCA5gd1xEQiiNmKCdzkgnH85BG@dpg-d8jlqgc2m8qs7398au8g-a/quiz_db_prwg'
    'SPRING_DATASOURCE_URL' = 'jdbc:postgresql://dpg-d8jlqgc2m8qs7398au8g-a:5432/quiz_db_prwg'
    'SPRING_DATASOURCE_USERNAME' = 'quiz_db_prwg_user'
    'SPRING_DATASOURCE_PASSWORD' = '0Zw4VDgCA5gd1xEQiiNmKCdzkgnH85BG'
    'SPRING_PROFILES_ACTIVE' = 'prod'
}
$envArray = @()
foreach ($k in $Vars.Keys) {
    $envArray += @{ key = $k; value = $Vars[$k]; secure = $false }
}
$body = @{ envVars = $envArray }
Write-Host "Putting env-vars..."
$resp = Invoke-RestMethod -Method Put -Uri "https://api.render.com/v1/services/$ServiceId/env-vars" -Headers $headers -Body ($body | ConvertTo-Json -Depth 5) -ContentType 'application/json'
Write-Host "Put response:"; $resp | ConvertTo-Json
