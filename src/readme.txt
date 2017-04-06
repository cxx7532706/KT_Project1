Author: Xuanxi Chen
StudentID: 747508

To compile, simply run JAVAC Main.java
To run, simply do JAVA Main.java

Before run, make sure make a folder first called 'src' under root, and put 'train.txt', 'test.txt' inside of the folder.

Main.GEDrun(String output) is the main method to do approximate match, will generate a txt called output for results.

Main.Analyse(String file, String outputFile) is the method to analyse data generated before to get which characters represent others. file is a result from previous method, outputFile is the output file name.

Main.Evaluate(String file) is the method to evaluate the precision of result.