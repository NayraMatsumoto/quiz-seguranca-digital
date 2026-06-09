param(
    [string]$ApiKey,
    [string]$ServiceId,
    [int]$TimeoutSec = 300,
    [int]$Interval = 5
)
$headers = @{ Authorization = "Bearer $ApiKey" }
$end = (Get-Date).AddSeconds($TimeoutSec)
$status = ''
while ((Get-Date) -lt $end) {
    $r = Invoke-RestMethod -Headers $headers -Uri "https://api.render.com/v1/services/$ServiceId/deploys?limit=1"
    if ($r -and $r[0] -and $r[0].deploy) { $status = $r[0].deploy.status }
    Write-Host ("[{0}] status={1}" -f (Get-Date -Format "HH:mm:ss"), $status)
    if ($status -in @('live','update_failed','update_succeeded','failed')) { break }
    Start-Sleep -Seconds $Interval
}
Write-Host "Final status: $status"