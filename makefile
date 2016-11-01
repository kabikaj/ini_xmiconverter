
OCRED_FILES=../../data/original/ocred_texts/*.xmi
ALTAFSIR_FILES=../../data/original/altafsir/*.xmi

XMICONVERTER_JAR=target/dependency/*:target/xmiconverter-0.0.1-SNAPSHOT.jar
JSONCONVERTER_JAR=target/dependency/*:target/jsonconverter-0.0.1-SNAPSHOT.jar

RM=/bin/rm -f

.PHONY : all clean help xmiconvert_ocred xmiconvert_altafsir xmiconvert_hadith
.PHONY : jsonconvert_ocred jsonconvert_altafsir jsonconvert_hadith

help:
	@echo "    clean"
	@echo "        Remove output xmi files"
	@echo "    xmiconvert"
	@echo "        Convert json to xmi files for all sources"
	@echo "    jsonconvert"
	@echo "        Convert xmi files to json for all sources"
	@echo ""
	@echo "    xmiconvert_ocred"
	@echo "        Convert source ocred json files to xmi"
	@echo "    xmiconvert_altafsir"
	@echo "        Convert source altafsir json files to xmi"
	@echo "    xmiconvert_hadith"
	@echo "        Convert source hadith alislam json files to xmi"
	@echo ""
	@echo "    jsonconvert_ocred"
	@echo "        Convert xmi to json for ocred files"
	@echo "    jsonconvert_altafsir"
	@echo "        Convert xmi to json for altafsir files"
	@echo "    jsonconvert_hadith"
	@echo "        Convert xmi to json for hadith alislam files"
	@echo "\nPlease run \"make xmiconvert\" or \"jsonconvert\""

clean:
	mvn clean dependency:copy-dependencies package
	$(RM) $(ALTAFSIR_FILES)
	$(RM) $(OCRED_FILES)


xmiconvert_ocred:	
	java -cp $(XMICONVERTER_JAR) xmiconverter/XmiConverterOcred

xmiconvert_altafsir:
	java -cp $(XMICONVERTER_JAR) xmiconverter/XmiConverterAltafsir

xmiconvert_hadith:
	java -cp $(XMICONVERTER_JAR) xmiconverter/XmiConverterHadith

xmiconvert: clean xmiconvert_ocred xmiconvert_altafsir xmiconvert_hadith


jsonconvert_ocred:	
	java -cp $(JSONCONVERTER_JAR) jsonconverter/JsonConverterOcred

jsonconvert_altafsir:
	java -cp $(JSONCONVERTER_JAR) jsonconverter/JsonConverterAltafsir

jsonconvert_hadith:
	java -cp $(JSONCONVERTER_JAR) jsonconverter/JsonConverterHadith

jsonconvert: clean jsonconvert_ocred jsonconvert_altafsir jsonconvert_hadith

