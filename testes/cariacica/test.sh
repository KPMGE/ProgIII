#!/bin/bash


# Parameters: 
testId=$1
dir=$2
srcDir=$3
tempDir=$4
baseDir=$5
testDir=$6
subIn=$7
subOut=$8
diffCmd=$9

# Variables.
tempFile="$tempDir/temp.txt"
outFile=output.txt
javaPrefix="[java]"

# Function: finds the index of a given substring ($1) in a string ($2).
indexOf() {
	substring=$1
	string=$2
	
	# If the substring is not present in the string, returns -1.
	if [[ $string == *"$substring"* ]]; then
		temp=${string/"$substring"*/}
		echo ${#temp}
	else 
		echo -1
	fi
}

# Runs the program.
cd $srcDir
ant run > $tempFile 2> /dev/null
exitcode=$?

# Copies to output.txt only the Java output.
rm $outFile 2> /dev/null
while read line; do
	idx=$(indexOf "$javaPrefix" "$line")
	if [ $idx -ne -1 ]; then
		echo "${line:7}" >> $outFile
	fi
done < $tempFile
cd - > /dev/null


# Checks if the program ran normally.
if [ $exitcode = 0 ]; then
	# Compares the result with the expected output.
	for output in `ls $testDir/$subOut`; do
		# Defines where to store the result of the diff.
		tempfile=$tempDir/${USER}_${dir}_test${testId}_${output}
	
		# Checks if the output file was even generated.
		if [ -f $srcDir/$output ]; then
			# Calculates the diff between the files.
			${diffCmd} $testDir/$subOut/$output $srcDir/$output > $tempfile
	
			# Checks if there has been error.
			if [ $? != 0 ]; then
				# Warns the user and outputs the difference.
				echo "[E] Testando $dir: teste ${testId}, diferenças em $output (professor <- -> aluno):"
				cat $tempfile
				echo ""
			else
				# Prints an OK message.
				echo "[I] Testando $dir: teste ${testId}, tudo OK em $output"
			fi
		
			# Cleanup (deletes the output file and the diff output file).
			rm -f $srcDir/${output} $tempfile
		else
			# Reports this error.
			echo "[E] Testando $dir: teste ${testId}, arquivo de saída não foi criado: $output!"
		fi
	done
else
	# Program returned with exit code different than 0. Something wrong happened.
	echo "[E] Testando $dir: teste ${testId}, erro na execução!  (Ant exit code $exitcode)"
fi
