pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
    
}
rootProject.name = "KPallete"


include(":android")
include(":desktop")
include(":common")
include(":tcpClient")
include(":KAS")
