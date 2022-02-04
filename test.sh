#!/bin/bash

# Constant definitions.
TEMP_DIR=/tmp
TEST_DIR=testes
TEST_SCRIPT=test.sh
BASE_DIR=$TEMP_DIR/prog3-tests-$(whoami)
SUBDIR_INPUT=in
SUBDIR_OUTPUT=out

# Procedure that tests one assignment.
test() {
	# Argument definition.
	dir=$1

	# Check if it's a directory, but not the teste directory.
	if [ -d $dir ] && [ "$dir" != "$TEST_DIR" ]; then
		echo "[I] Testando $dir..."

		# Removes all files from the base folder and copy the assignment there.		
		rm -rf $BASE_DIR/*
		cp -rf $dir/* $BASE_DIR/
		srcdir=$BASE_DIR

		### COMPILATION ###
		# Compiles the program. Uses a common base directory so output is the same.
		cd $srcdir
		ant compile > /dev/null 2> /dev/null
		exitcode=$?
		cd - > /dev/null

		# Checks if the program compiled normally.
		if [ $exitcode = 0 ]; then
		
			### EXECUTION ###
			# Runs the program with input files from all test folders.
			for subdir in `ls $TEST_DIR`; do
				# Copies the files from the test input folder.
				echo "[I] Testando $dir: teste $subdir"
				cp -f $TEST_DIR/$subdir/$SUBDIR_INPUT/* $srcdir/
				
				# Runs the test.
				$TEST_DIR/$subdir/$TEST_SCRIPT "$subdir" "$dir" "$srcdir" "$TEMP_DIR" "$BASE_DIR" "$TEST_DIR/$subdir" "$SUBDIR_INPUT" "$SUBDIR_OUTPUT" "diff --ignore-all-space --ignore-blank-lines -W 300"
				
				# Cleanup (removes all input files).
				cd $srcdir
				ant clean > /dev/null 2> /dev/null
				cd - > /dev/null
			done
		else
			# Compilation returned with exit code different than 0. Something wrong happened.
			echo "[E] Testando $dir: erro de compilação! (Ant exit code $exitcode)"
		fi
		
		echo "[I] Testando $dir: pronto!"
		echo 
		echo 
	fi
}

#########################
## SCRIPT BEGINS HERE: ##
#########################

echo "Script de teste PROG3 - Trabalho 1"
echo

# Creates the base folder.
mkdir -p $BASE_DIR

# Checks if a specific folder was specified, otherwise test all assignments.
if [ "$1" != "" ] && [ -d $1 ]; then
	# A directory name was given. Process only that directory.
	test $1
else
	# No directory name given. Process all directories on assignments directory.
	for dir in *; do test "$dir"; done
fi
