JFLEX  = java -jar jflex.jar
BYACCJ = ./yacc.linux -tv -J
JAVAC  = javac
OUTPUT = output

# Cria a pasta OUTPUT se ela não existir
all: $(OUTPUT) Parser.class AppTeste.class run

run: Parser.class
	java -cp $(OUTPUT) Parser ./test_rec.txt

build: clean Parser.class

clean:
	rm -rf $(OUTPUT)/*

$(OUTPUT):
	mkdir -p $(OUTPUT)

AppTeste.class: Yylex.java Parser.java *.java
	$(JAVAC) -d $(OUTPUT) AppTeste.java

Parser.class: Yylex.java Parser.java *.java
	$(JAVAC) -d $(OUTPUT) Parser.java

Yylex.java: calc.flex
	$(JFLEX) calc.flex

Parser.java: calc.y
	$(BYACCJ) calc.y
	mv y.output $(OUTPUT)/