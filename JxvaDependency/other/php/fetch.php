<?php
  error_reporting(7);
  $fp = fopen ("http://passport.ztemc.com/", "r");
  if (!$fp) {
    echo "Unable to open remote file.\n";
    exit;
  }
  while($fc = fread($fp, 8192)){
       $content .= $fc;
  }
  echo $content;
  fclose($fp); 
?>