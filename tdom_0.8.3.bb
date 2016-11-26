DESCRIPTION = "XML data processing in Tcl"
LICENSE = "MPL-1"
SECTION = "devel/tcltk"
HOMEPAGE = "http://tdom.github.io/"
DEPENDS = "tcl"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=4673aaff544d4c9b9a521cb8e0860bfb"
PR = "r0"

SRC_URI = "git://github.com/tDOM/tdom.git;protocol=git;tag=master"

S = "${WORKDIR}/git/unix"

inherit autotools

FILES_${PN} += "${libdir}/tdom0.8.3/"

CONFIGUREOPTS = " \
             --build=${BUILD_SYS} \
	     --host=${HOST_SYS} \
             --target=${TARGET_SYS} \
"
EXTRA_OECONF = " \
             --with-tcl=${STAGING_BINDIR_CROSS} \
             --with-tclinclude=${STAGING_INCDIR} \
             "

do_configure() {
        oe_runconf
}

oe_runconf () {
	cfgscript="${S}/../configure"
	if [ -x "$cfgscript" ] ; then
		bbnote "Running $cfgscript ${CONFIGUREOPTS} ${EXTRA_OECONF} $@"
		set +e
		${CACHED_CONFIGUREVARS} $cfgscript ${CONFIGUREOPTS} ${EXTRA_OECONF} "$@"
		if [ "$?" != "0" ]; then
			echo "Configure failed. The contents of all config.log files follows to aid debugging"
			find ${S} -name config.log -print -exec cat {} \;
			bbfatal "oe_runconf failed"
		fi
		set -e
	else
		bbfatal "no configure script found at $cfgscript"
	fi
}
