package org.nlogo.extensions.vid

import javafx.embed.swing.JFXPanel

import com.github.sarxos.webcam.Webcam

import org.scalatest.{ BeforeAndAfter, FunSuite }

class CameraTest extends FunSuite with BeforeAndAfter {
  val _ = new JFXPanel() // init JavaFX

  def hasCameras = Camera.cameraNames.nonEmpty

  if (hasCameras) {
    test("default camera name is the first available camera name") {
      assert(Camera.cameraNames.head == Camera.defaultCameraName.get)
    }

    test("trying to open a camera that doesn't exist should return None") {
      assert(Camera.open("notreal") == None)
    }

    test("trying to open a camera that exists should return a VideoSource") {
      val camera = Camera.open(Camera.defaultCameraName.get)
      assert(camera.isDefined)
    }

    test("creating a camera with a webcam opens it") {
      new CameraTestHelper {
        assert(webcam.isOpen)
      }
    }

    test("close closes an already-opened camera") {
      new CameraTestHelper {
        cam.close()
        assert(! webcam.isOpen)
      }
    }

    test("can move between playing and paused") {
      new CameraTestHelper {
        assert(cam.isPlaying)
        cam.stop()
        assert(! cam.isPlaying)
        assert(webcam.isOpen)
        cam.play()
        assert(cam.isPlaying)
        assert(webcam.isOpen)
      }
    }

    test("captures a buffered image") {
      new CameraTestHelper {
        val image = cam.captureImage()
        assert(image != null)
      }
    }

    test("when stopped, returns the same bufferedImage each time captureImage is called") {
      new CameraTestHelper {
        cam.stop()
        val image = cam.captureImage()
        assert(image == cam.captureImage())
      }
    }

    trait CameraTestHelper {
      val webcam = Webcam.getDefault
      val cam = new Camera(webcam)
    }
  }
}
