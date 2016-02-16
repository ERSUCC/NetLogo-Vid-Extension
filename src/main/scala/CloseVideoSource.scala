package org.nlogo.extensions.vid

import org.nlogo.core.Syntax
import org.nlogo.api.{ Argument, Command, Context }

class CloseVideoSource(vid: VideoSourceContainer) extends Command {
  override def getSyntax = Syntax.commandSyntax(List())

  def perform(args: Array[Argument], context: Context): Unit = {
    vid.videoSource.foreach { v =>
      v.close()
    }
    vid.videoSource = None
  }
}
