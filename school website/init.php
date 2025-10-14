<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>HTML 5 Boilerplate</title>
    <link rel="stylesheet" href="style.css">
  </head>
  <body>

 

<?php
$servername = "localhost";
$username = "root";
$password = ""; // Update if needed
$dbname = "assignment8";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}




$sql = "SELECT ID, Name, Type, Make, Model, Brand FROM items";
$result = $conn->query($sql);


if ($result = $mysqli -> query("SELECT * FROM items")) {
  echo "Returned rows are: " . $result -> num_rows;
  // Free result set
  $result -> free_result();
}




$conn->close();

$file = 'input.csv';


if (($handle = fopen($file, "r")) !== FALSE) {
    echo "<input type='text' id='searchInput' onkeyup='searchTable()' placeholder='Search for names..' />"; // Search input box
    echo "<table id='myTable' border='1'>";
 
    // Use echo to print to the screen the html and table start tags

    if (($headers = fgetcsv($handle, 1000, ",")) !== FALSE) {
        echo "<tr>"; // Start the header row
        
        // Output each header as a table header cell
        foreach ($headers as $header) {
            echo "<th>" . htmlspecialchars($header) . "</th>"; // Output header cell
        }
        
        echo "</tr>"; // End the header row
    }

    // Read the CSV file line by line
    while (($data = fgetcsv($handle, 1000, ",")) !== FALSE) {
        echo "<tr>"; 
        
        foreach ($data as $field) {
            echo "<td>" . htmlspecialchars($field) . "</td>"; 
        }
        
        echo "</tr>"; 
    }
    
    echo "</table>"; 
    fclose($handle); // Close the file
} else {
    echo "Could not open the file.";
}

?>


  
  </body>
</html>