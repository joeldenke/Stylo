package se.denke.gradle

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.the
import org.gradle.plugin.use.PluginDependency

internal val Project.versionCatalog: VersionCatalog
  get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Project.libs: LibrariesForLibs
  get() = the<LibrariesForLibs>()

internal fun PluginManager.alias(pluginDependency: org.gradle.api.provider.Provider<PluginDependency>) {
  apply(pluginDependency.get().pluginId)
}

/*val PluginDependenciesSpecScope.`libs`: LibrariesForLibsInPluginsBlock
  get() = versionCatalogForPluginsBlock("libs") as LibrariesForLibsInPluginsBlock*/
