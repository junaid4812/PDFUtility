# PDFUtility
Java based PDF utility for performing basic merge, split and extract attachments from the pdf file

This utility can perform following actions
1. Merge PDF Documents
2. Split PDF Document
3. Extract Attachment from PDF Documents

**Input Details:**
usage: PDF Utility Info
-a,--action <arg>        PDF Action e.g. Merge, Split, Extract
-d,--destination <arg>   Destination directory, default destination is
the output directory at tool location
-f,--filename <arg>      Filename - Acts as input for Split & Extract
action and as output name for merge action
-n,--pagenumber <arg>    Page number to split the file
-p,--password <arg>      Password for encrypted pdf
-s,--source <arg>        Source Directory


**Merge Action:**
* Mandatory Inputs: (-s or --source)
* Optional Inputs: (-d or --destination), (-f or --filename), (-p or --password)
* Ignored Inputs: (-n or --pagenumber)
PDF files available under the source location will be merged. 
Sorting order will be based on the ascending filenames in the source directory. 
If filename parameter is passed, it will be used as output filename.
Password is required if the files are encrypted.

**Split Action:**
* Mandatory Inputs: (-s or --source), (-f or --filename), (-n or --pagenumber)
* Optional Inputs: (-d or --destination),  (-p or --password)
* Ignored Inputs: None
Source directory and PDF file name to be split is mandatory for this action.
Additionally, page number is also mandatory for splitting the files. 
This tool splits the source document into files having given page numbers
value of n should be greater than 0

**Extract Action:**
* Mandatory Inputs: (-s or --source)
* Optional Inputs: (-d or --destination), (-p or --password), (-f or --filename)
* Ignored Inputs: (-n or --pagenumber)

Source directory is mandatory for this action.
All the PDF files available under this source directory will be checked to extract 
the attachments, If filename is passed then only that PDF is processed

Note: 
* For all the above actions, 
  * Password will be mandatory if the input pdf is encrypted
  * Destination directory is optional, if not passed, tool will create a directory

command to run: 

$ mvn clean package