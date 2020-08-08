MrCadsApp.java requires allocation of the fxmlMrcardsApp.fxml file in the bin/mrcards directory.

Also project expects "resources" folder with .txt file which includes custom data.

In "resources" folder allocate sourceFile.txt and log.txt files.

sourceFile.txt - custom 16 byte data (FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF) per line.

log.txt - file with initial string which is used as milestone. Thus at the very first time the initial entry in the both files
should be #.
