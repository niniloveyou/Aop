package deadline.plugin

import deadline.plugin.asm.AsmRestoreTransform
import deadline.plugin.javassist.JavassistRestoreTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

public class RestorePlugin implements Plugin<Project> {

    void apply(Project project) {
        def logger = project.logger
        logger.error  "<<<<<<<<<<<<<<<<<<<<<<<<<<"
        logger.error  "RestorePlugin start work!"
        logger.error ">>>>>>>>>>>>>>>>>>>>>>>>>>"

        project.android.registerTransform(new JavassistRestoreTransform(project))
    }
}