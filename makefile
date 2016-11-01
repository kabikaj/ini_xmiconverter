
OCRED_FILES=../../data/original/ocred_texts/*.xmi
ALTAFSIR_FILES=../../data/original/altafsir/*.xmi

.PHONY : all convert_ocred convert_altafsir clean help

all: clean convert_ocred convert_altafsir

convert_ocred:	
	java -cp target/dependency/*:target/xmiconverter-0.0.1-SNAPSHOT.jar xmiconverter/XmiConverterOcred

convert_altafsir:	
	java -cp target/dependency/*:target/xmiconverter-0.0.1-SNAPSHOT.jar xmiconverter/XmiConverterAltafsir

clean:
	mvn clean dependency:copy-dependencies package
	/bin/rm -f  $(ALTAFSIR_FILES)
	/bin/rm -f  $(OCRED_FILES)

help:
	@echo "    clean"
	@echo "        Remove output xmi files"
	@echo "    convert_ocred"
	@echo "        Convert source ocred json files to xmi"
	@echo "    convert_altafsir"
	@echo "        Convert source altafsir json files to xmi"
	@echo "    all"
	@echo "        Clean, remove xmi files and execute conversions"
