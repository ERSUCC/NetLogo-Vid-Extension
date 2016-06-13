package org.nlogo.extensions.vid

import org.nlogo.core.Syntax
import org.nlogo.api.{ Argument, Command, Context, ExtensionException }

class StartSource(vid: VideoSourceContainer) extends Command {
  def getSyntax = Syntax.commandSyntax(List())

  def perform(args: Array[Argument], context: Context): Unit = {
    if (vid.videoSource.isEmpty)
      throw new ExtensionException("vid: no selected source")
    vid.videoSource.foreach(_.play())
  }
}
