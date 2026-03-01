java -cp target/classes:target/dependency/* checkly.report.flush.FlushReport

# Check if the Java program executed successfully
if [ $? -eq 0 ]; then
  echo "Report generation completed successfully!"
else
  echo "Report generation failed!"
fi