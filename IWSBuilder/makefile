LIBRARY:=$(shell jq '.library' -r ./config.json)
LIBRARY_DESC:=$(shell jq '.libraryDesc' -r ./config.json)
DBGVIEW=*SOURCE
DIR_CLP=src/main/qcllesrc
DIR_CMD=src/main/qcmdsrc
EXT_CLP=CLLE
EXT_CMD=CMD
ROOT_DIR:=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))

CLPS:=\
	$(patsubst %.$(EXT_CLP),%.clps,$(wildcard $(DIR_CLP)/*.$(EXT_CLP)))

CMDS=\
	$(patsubst %.$(EXT_CMD),%.command,$(wildcard $(DIR_CMD)/*.$(EXT_CMD)))
	
SRCFILES_0=\
	$(DIR_CLP) \
	$(DIR_CMD)

SRCFILES=\
	$(SRCFILES_0:=.srcpf)


# Ensure that intermediate files created by rules chains don't get
# automatically deleted
.PRECIOUS: %.clp %.lib

all: build	

build: $(LIBRARY).lib \
		create-srcfiles \
		build-cmds \
		build-clps

build-clps: $(CLPS)

build-cmds: $(CMDS)

create-srcfiles: $(SRCFILES)

install-iwss: $(IWSS)

display-vars: 
	$(info    CLPS is $(CLPS))
	$(info    CMDS is $(CMDS)) 
	$(info    SRCFILES is $(SRCFILES)) 

%.lib: 
	(system -Kp "CHKOBJ $* *LIB" || system -Kp "CRTLIB $* TEXT('$(LIBRARY_DESC)')") && \
	touch $@

%.command: 
	$(call substitute,$*.$(EXT_CMD),$@)
	system -Kp "CRTCMD CMD($(LIBRARY)/$(notdir $*)) PGM($(LIBRARY)/$(notdir $*)) SRCSTMF('$(ROOT_DIR)/$<')"

%.clps: 
	# @echo "$$@=$@ $$%=$% $$<=$< $$?=$? $$^=$^ $$+=$+ $$|=$| $$*=$*"
	$(call substitute,$*.$(EXT_CLP),$@)  && \
	system -Kp "CRTBNDCL PGM($(LIBRARY)/$(notdir $*)) SRCSTMF('$(ROOT_DIR)/$*.$(EXT_CLP)') DFTACTGRP(*NO) ACTGRP(*NEW) DBGVIEW($(DBGVIEW)) REPLACE(*YES) INCDIR('$(ROOT_DIR)') OUTPUT(*NONE)" && \
	$(call copy_to_srcpf,$(ROOT_DIR)/$@,$(LIBRARY),QCLLESRC,$(notdir $*)) || \
	-rm $@

%.srcpf: $(LIBRARY).lib
	system -Kp "CRTSRCPF FILE($(LIBRARY)/$(notdir $*)) RCDLEN(240) MBR(*NONE) TEXT('just for read-only')" && \
	touch $@

clean: clean-srcfiles \
		clean-clps \
		clean-lib

clean-srcfiles:
	rm -f src/main/*.srcpf

clean-clps:
	rm -f $(DIR_CLP)/*.clps

clean-cmds:
	rm -f $(DIR_CMD)/*.command

clean-lib:
	rm -f $(LIBRARY).lib &&\
	system -Kp 'DLTLIB $(LIBRARY)' || :	

define copy_to_srcpf
	system -Kp "CPYFRMSTMF FROMSTMF('$(1)') TOMBR('/QSYS.LIB/$(2).LIB/$(3).FILE/$(4).MBR') MBROPT(*REPLACE) STMFCCSID(*STMF) DBFCCSID(*FILE) ENDLINFMT(*ALL)" && \
	system -Kp "CHGPFM FILE($(2)/$(3)) MBR($(4)) SRCTYPE($(subst .,,$(suffix $(1)))) TEXT('just for read-only')"
endef

define substitute
	-rm $(2)
	export QIBM_CCSID=$(SHELL_CCSID) && touch $(2) && \
	sed 's/$$(LIBRARY)/$(LIBRARY)/g; s/$$(ROOT_DIR)/$(subst /,\/,$(ROOT_DIR))/g; s/$$(DIR_JAVA)/$(subst /,\/,$(DIR_JAVA))/g' $(1) >> $(2)
endef	