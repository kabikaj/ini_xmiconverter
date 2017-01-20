
PARENT_DIR=../../data/files/original

OCRED_FILES=$(PARENT_DIR)/ocred_texts/*.xmi
ALTAFSIR_FILES=$(PARENT_DIR)/altafsir/*.xmi
HADITH_FILES=$(PARENT_DIR)/hadith_alislam/*.xmi

JAR=target/dependency/*:target/ini_xmiconverter-0.0.1-SNAPSHOT.jar
PKG=ini_xmiconverter

RM=/bin/rm -f
JAVA=java -cp $(JAR)

.PHONY : all clean help convert_ocred convert_altafsir convert_hadith

all: clean convert_ocred convert_altafsir convert_hadith

help:
	@echo "    all"
	@echo "        Clean, get json files from sources, convert to xmi, and dump them into output_path [DEFAULT"]
	@echo "    convert_ocred"
	@echo "        Convert only files from source ocred_texts"
	@echo "    convert_altafsir"
	@echo "        Convert only files from source altafsir"
	@echo "    convert_hadith"
	@echo "        Convert only files from source hadith_alislam"
	@echo "    clean"
	@echo "         Clean resources and remove files from output_path"
	@echo ""
	@echo "usage: make [help] [all] [convert_ocred] [convert_altafsir] [convert_hadith] [clean]"

clean:
	mvn clean dependency:copy-dependencies package
	$(RM) $(ALTAFSIR_FILES)
	$(RM) $(OCRED_FILES)
	$(RM) $(HADITH_FILES)

convert_ocred:	
	$(JAVA) $(PKG)/XmiConverterOcred

convert_altafsir:
	$(JAVA) $(PKG)/XmiConverterAltafsir

convert_hadith:
	$(JAVA) $(PKG)/XmiConverterHadith


