#
# make test (or just: make)
#    generates lexer & parser, compiles all *.java files, and runs test
#

# main class
MAIN = Main

# test data
TEST_IN  = src/test/pa3/pass/0.in
OUT_GOOD = src/test/pa3/pass/0.s

# jflex input and output
LEXER_IN = src/main/jflex/jlite.flex
LEXER_CLASS = Lexer

# cup file
PARSER_IN = src/main/cup/jlite.cup

# This file is included in all example Makefiles.
# It defines targets and variables common to all examples.

# Paths are relative to the example project directories

JFLEX      = java -jar ./bin/jflex-full-1.8.2.jar

LIB        = ../common/lib
CUPVERSION = java-cup-11b.jar
CUPJAR     = ./bin/$(CUPVERSION)
CUP        = java -jar $(CUPJAR)

OUT    = out
GEN    = $(OUT)/genfiles
MAIN_JAVA_FILES ?= src/main/java/*.java  src/main/java/arm/*.java src/main/java/ir3/stmt/*.java src/main/java/*.java src/main/java/ir3/exp/*.java src/main/java/*.java src/main/java/ir3/*.java src/main/java/*.java src/main/java/staticcheckers/*.java src/main/java/staticcheckers/type/*.java src/main/java/parsetree/*.java src/main/java/parsetree/**/*.java
MORE_JAVA_FILES ?=

LEXER_CLASS ?= Yylex
LEXER  = $(GEN)/$(LEXER_CLASS).java

PARSER_OUT ?= parser.java sym.java
PARSER ?= $(addprefix $(GEN)/,$(PARSER_OUT))

OUT_ACTUAL = $(OUT)/output.txt


all: test

compile: $(OUT)/$(MAIN).class

$(OUT)/$(MAIN).class: $(LEXER) $(PARSER) $(MAIN_JAVA_FILES) $(MORE_JAVA_FILES)
	javac -cp $(CUPJAR):$(OUT) -d $(OUT) $(MAIN_JAVA_FILES) $(GEN)/*.java

gen_jflex: $(LEXER)

$(LEXER): $(LEXER_IN)
	$(JFLEX) -d $(GEN) $<

gen_cup: $(PARSER)

$(PARSER): $(PARSER_IN) $(CUPJAR)
	$(CUP) -interface < $<
	mkdir -p $(GEN)
	mv $(PARSER_OUT) $(GEN)/

$(CUPJAR):
	mkdir -p $(LIB)
	(cd $(LIB); curl -O $(CUPURL)/$(CUPVERSION))

run: compile $(TEST_IN)
	java -cp $(CUPJAR):$(OUT) $(MAIN) $(PIPE_INPUT) $(TEST_IN) > $(OUT_ACTUAL)
	@cat $(OUT_ACTUAL)

test: run
	@(diff $(OUT_ACTUAL) $(OUT_GOOD) && echo "Test OK!") || (echo "Test FAILED"; exit 1)

clean:
	rm -rf $(OUT)
