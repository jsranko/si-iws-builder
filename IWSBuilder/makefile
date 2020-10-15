LIBRARY:=$(shell jq '.library' -r ./config.json)
LIBRARY_DESC:=$(shell jq '.libraryDesc' -r ./config.json)
SHELL_CCSID:=$(shell jq '.pase_ccsid' -r ./config.json)
DBGVIEW=*SOURCE
DIR_SRC=src/main
DIR_RPG=$(DIR_SRC)/qrpglesrc
DIR_BND=$(DIR_SRC)/qsrvsrc
DIR_CLP=$(DIR_SRC)/qcllesrc
DIR_CMD=$(DIR_SRC)/qcmdsrc
DIR_IWSS=$(DIR_SRC)/qiwsssrc
DIR_CPY=$(DIR_SRC)/qcpylesrc
EXT_IWSS=IWSS
EXT_IWSSCONF=iwssconf
EXT_CLP=CLLE
EXT_CMD=CMD
EXT_CPY=*RPGLE
ROOT_DIR:=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
JAR_SINGLE=$(ROOT_DIR)/target/si-iws-builder-jar-with-dependencies.jar
JAR_CLASSNAME=de.sranko_informatik.ibmi.iwsbuilder.App

CLPS:=\
	$(patsubst %.$(EXT_CLP),%.clps,$(wildcard $(DIR_CLP)/*.$(EXT_CLP)))

CPYS:=\
	$(patsubst %.$(EXT_CPY),%.cpys,$(wildcard $(DIR_CLP)/*.$(EXT_CPY)))

CMDS=\
	$(patsubst %.$(EXT_CMD),%.command,$(wildcard $(DIR_CMD)/*.$(EXT_CMD)))
	
SRCFILES_0=\
	$(DIR_CLP) \
	$(DIR_RPG) \
	$(DIR_BND) \
	$(DIR_CPY) \
	$(DIR_CMD) \
	$(DIR_IWSS)

SRCFILES=\
	$(SRCFILES_0:=.srcpf)
	
PGMS:=\
	$(patsubst %.RPGLE,%.pgm,$(shell grep -il " main" $(DIR_RPG)/*.SQLRPGLE))
	
SRVPGMS:=\
	$(patsubst %.RPGLE,%.srvpgm,$(shell grep -il " nomain" $(DIR_RPG)/*.SQLRPGLE))

IWSS=\
	$(patsubst %.$(EXT_IWSS),%.$(EXT_IWSSCONF),$(wildcard $(DIR_IWSS)/*.$(EXT_IWSS)))


# Ensure that intermediate files created by rules chains don't get
# automatically deleted
.PRECIOUS: %.clp %.lib %.pgm %.command %.srcpf

all: build	

build: $(LIBRARY).lib \
		create-srcfiles \
		build-pgms \
		build-srvpgms \
		build-clps \
		build-cmds \
		install-iwss

build-srvpgms: $(SRVPGMS)

build-pgms: $(PGMS)

build-clps: $(CLPS)

build-cmds: $(CMDS)

create-srcfiles: $(SRCFILES)

install-iwss: $(IWSS)

display-vars: 
	$(info    CLPS is $(CLPS))
	$(info    CMDS is $(CMDS)) 
	$(info    SRCFILES is $(SRCFILES)) 
	$(info    SRVPGMS is $(SRVPGMS)) 
	$(info    PGMS is $(PGMS)) 
	$(info    CPYS is $(CPYS)) 
	$(info    IWSS is $(IWSS)) 

%.lib: 
	(system -Kp "CHKOBJ $* *LIB" || system -Kp "CRTLIB $* TEXT('$(LIBRARY_DESC)')") && \
	touch $@

%.command: 
	# @echo "$$@=$@ $$%=$% $$<=$< $$?=$? $$^=$^ $$+=$+ $$|=$| $$*=$*"	
	$(call substitute,$*.$(EXT_CMD),$@)
	system -Kp "CRTCMD CMD($(LIBRARY)/$(notdir $*)) PGM($(LIBRARY)/$(notdir $*)) SRCSTMF('$(ROOT_DIR)/$*.$(EXT_CMD)')"

%.clps: 
	# @echo "$$@=$@ $$%=$% $$<=$< $$?=$? $$^=$^ $$+=$+ $$|=$| $$*=$*"
	$(call substitute,$*.$(EXT_CLP),$@)  && \
	system -Kp "CRTBNDCL PGM($(LIBRARY)/$(notdir $*)) SRCSTMF('$(ROOT_DIR)/$*.$(EXT_CLP)') DFTACTGRP(*NO) ACTGRP(*NEW) DBGVIEW($(DBGVIEW)) REPLACE(*YES) INCDIR('$(ROOT_DIR)/$(DIR_SRC)') OUTPUT(*NONE)" && \
	$(call copy_to_srcpf,$(ROOT_DIR)/$@,$(LIBRARY),QCLLESRC,$(notdir $*)) || \
	-rm $@

%.srcpf: $(LIBRARY).lib
	system -Kp "CRTSRCPF FILE($(LIBRARY)/$(notdir $*)) RCDLEN(240) MBR(*NONE) TEXT('just for read-only')" && \
	touch $@

%.srvpgm: %.module
	$(call copy_to_srcpf,$(ROOT_DIR)/$(DIR_BND)/$(notdir $*).BND,$(LIBRARY),$(notdir $(DIR_BND)),$(notdir $*))
	system -Kp "CRTSRVPGM SRVPGM($(LIBRARY)/$(notdir $*)) MODULE($(LIBRARY)/$(notdir $*)) SRCSTMF('$(ROOT_DIR)/$(DIR_BND)/$(notdir $*).BND') ACTGRP(*CALLER) OPTION(*DUPPROC) STGMDL(*INHERIT)" && \
	touch $@ 
	system -Kp "DLTMOD MODULE($(LIBRARY)/$(notdir $*))"
	
%.module: %.RPGLE
	$(call copy_to_srcpf,$(ROOT_DIR)/$<,$(LIBRARY),$(notdir $(DIR_RPG)),$(notdir $*))
	system -Kp "CRTRPGMOD MODULE($(LIBRARY)/$(notdir $*)) SRCSTMF('$(ROOT_DIR)/$<') DBGVIEW($(DBGVIEW)) REPLACE(*YES) INCDIR('$(ROOT_DIR)/$(DIR_SRC)') STGMDL(*INHERIT) TGTCCSID(*JOB) OUTPUT(*NONE)"  && \
	touch $@	

%.pgm: %.SQLRPGLE
	$(call copy_to_srcpf,$(ROOT_DIR)/$<,$(LIBRARY),$(notdir $(DIR_RPG)),$(notdir $*))	
	system "CRTSQLRPGI OBJ($(LIBRARY)/$(notdir $*)) SRCSTMF('$(ROOT_DIR)/$<') OBJTYPE(*PGM) DBGVIEW($(DBGVIEW)) RPGPPOPT(*LVL2) REPLACE(*YES) COMPILEOPT('INCDIR(''$(ROOT_DIR)/$(DIR_SRC)'') TGTCCSID(*JOB) DFTACTGRP(*NO) ACTGRP(*NEW) OUTPUT(*NONE)')" && \
	touch $@

%.pgm: %.RPGLE
	$(call copy_to_srcpf,$(ROOT_DIR)/$<,$(LIBRARY),$(notdir $(DIR_RPG)),$(notdir $*))
	system -Kp "CRTBNDRPG PGM($(LIBRARY)/$(notdir $*)) SRCSTMF('$(ROOT_DIR)/$<') DFTACTGRP(*NO) ACTGRP(*NEW) DBGVIEW($(DBGVIEW)) REPLACE(*YES) INCDIR('$(ROOT_DIR)/$(DIR_SRC)') TGTCCSID(*JOB) OUTPUT(*NONE)" && \
	touch $@

%.module: %.SQLRPGLE
	$(call copy_to_srcpf,$(ROOT_DIR)/$<,$(LIBRARY),$(notdir $(DIR_RPG)),$(notdir $*))
	system -Kp "CRTSQLRPGI OBJ($(LIBRARY)/$(notdir $*)) SRCSTMF('$(ROOT_DIR)/$<') OBJTYPE(*MODULE) RPGPPOPT(*LVL2) DBGVIEW($(DBGVIEW)) REPLACE(*YES) COMPILEOPT('INCDIR(''$(ROOT_DIR)'') OUTPUT(*NONE) TGTCCSID(*JOB) STGMDL(*INHERIT)')" && \
	touch $@

%.$(EXT_IWSSCONF): %.$(EXT_IWSS)
	$(call substitute,$*.$(EXT_IWSS),$@)
	java -cp $(JAR_SINGLE) $(JAR_CLASSNAME) ./$@ && \
	$(call copy_to_srcpf,$(ROOT_DIR)/$@,$(LIBRARY),$(notdir $(DIR_IWSS)),$(notdir $*))

clean: clean-srcfiles \
		clean-clps \
		clean-cmds \
		clean-pgms \
		clean-iwss \
		clean-lib

clean-srcfiles:
	rm -f src/main/*.srcpf

clean-clps:
	rm -f $(DIR_CLP)/*.clps

clean-cmds:
	rm -f $(DIR_CMD)/*.command

clean-pgms:
	rm -f $(DIR_RPG)/*.pgm

clean-lib:
	rm -f $(LIBRARY).lib &&\
	system -Kp 'DLTLIB $(LIBRARY)' || :	

clean-iwss:
	rm -f $(DIR_IWSS)/*.$(EXT_IWSSCONF)

define copy_to_srcpf
	system -Kp "CPYFRMSTMF FROMSTMF('$(1)') TOMBR('/QSYS.LIB/$(2).LIB/$(3).FILE/$(4).MBR') MBROPT(*REPLACE) STMFCCSID(*STMF) DBFCCSID(*FILE) ENDLINFMT(*ALL)" && \
	system -Kp "CHGPFM FILE($(2)/$(3)) MBR($(4)) SRCTYPE($(subst .,,$(suffix $(1)))) TEXT('just for read-only')"
endef

define substitute
	-rm $(2)
	export QIBM_CCSID=$(SHELL_CCSID) && touch $(2) && \
	sed 's/$$(LIBRARY)/$(LIBRARY)/g; s/$$(JAR_SINGLE)/$(subst /,\/,$(JAR_SINGLE))/g; s/$$(ROOT_DIR)/$(subst /,\/,$(ROOT_DIR))/g; s/$$(DIR_JAVA)/$(subst /,\/,$(DIR_JAVA))/g' $(1) >> $(2)
endef	