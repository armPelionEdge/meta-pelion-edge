DESCRIPTION = "Pelion Relay-Terminal for web based ssh sessions"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

RT_SERVICE_FILE = "pelion-relay-term.service"
PR = "r1"
S = "${WORKDIR}/git/relay-term"

FILES_${PN} += "\
    ${systemd_system_unitdir}/${PN}-watcher.service\
    ${systemd_system_unitdir}/${PN}-watcher.path\
    /wigwag/wigwag-core-modules/*\
    /wigwag/devicejs-core-modules/*\
    "

SRC_URI = "git://git@github.com/armPelionEdge/edge-node-modules.git;protocol=https \
npmsw://${THISDIR}/files/npm-shrinkwrap.json \
file://${RT_SERVICE_FILE} \
file://${PN}-watcher.service \
file://${PN}-watcher.path \
"

SRCREV = "b72acd69a13c4cb474e6ff96eb78bb9de7e99c45"

inherit pkgconfig systemd npm

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "${RT_SERVICE_FILE} \
${PN}-watcher.service \
${PN}-watcher.path"

SYSTEMD_AUTO_ENABLE_${PN} = "enable"

DEPENDS ="nodejs"
RDEPENDS_${PN} += "bash nodejs"

do_install() {
    cd ${S}
    install -d ${D}${systemd_system_unitdir}
    install -m 755 ${WORKDIR}/${RT_SERVICE_FILE} ${D}${systemd_system_unitdir}/${RT_SERVICE_FILE}
    install -m 755 ${WORKDIR}/${PN}-watcher.service ${D}${systemd_system_unitdir}/${PN}-watcher.service
    install -m 755 ${WORKDIR}/${PN}-watcher.path ${D}${systemd_system_unitdir}/${PN}-watcher.path

    install -d ${D}/wigwag/devicejs-core-modules/node_modules
    cp -r ${S}/node_modules ${D}/wigwag/devicejs-core-modules/node_modules

    install -d ${D}/wigwag/wigwag-core-modules
    cp -r ${S} ${D}/wigwag/wigwag-core-modules/relay-term
}