package com.harishkannarao.util

import com.harishkannarao.config.PerformanceTestConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

object SpringUtil {
  val ctx: ApplicationContext = new AnnotationConfigApplicationContext(classOf[PerformanceTestConfiguration])
}