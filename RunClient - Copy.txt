
$jarName = "your-project-1.0.0.jar"
$mainClass = "com.example.client.ChatClientGUI"
$targetDir = "target"
$fullPath = "$targetDir\$jarName"
if (-not (Test-Path -Path $fullPath -PathType Leaf)) 
{
    Write-Host "Error: JAR file not found at $fullPath" -ForegroundColor Red
    Write-Host "Please build the project first with 'mvn clean package'" -ForegroundColor Yellow
    exit 1
}

$javaCommand = "java -cp $fullPath $mainClass"

Write-Host "Starting ChatClientGUI..." -ForegroundColor Green
Write-Host "Executing: $javaCommand" -ForegroundColor Cyan

try 
{
    Invoke-Expression $javaCommand
}
catch 
{
    Write-Host "Error running the application: $_" -ForegroundColor Red
    exit 1
}