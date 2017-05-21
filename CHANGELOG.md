# Changelog
## Version 1.1.0
- Removed the `RecordingListener` interface. (causes incompatibility with older versions)
- Removed the `dispose` method from `Recorder`. (causes incompatibility with older versions)
- Added a new `start` method to `Recorder` which takes in an `OutputStream` on which the recorded data will be written.
- Added the method `Audio.getAudioInputStream(URL)`.
- Added some system specific file formats.
- Added the class `Playlist`.
- Added the interface `Playable` and moved some methods from `Audio` to there.
- Fixed some odd behavior with the `StreamedAudio.resume()` method.
- Fixed weird control behavior.
- The constructors of `StreamedAudio` and `BufferedAudio` now all throw an `AudioException`. (causes incompatibility with older versions)
- Added the method `Audio.getHeaders()`. This works for all supported formats except `FileFormat.OGG`.
- Added the method `Audio.isOpen()`.
- Added the `AudioListener` class.
- Removed the `Audio.ends()` method. You should use an `AudioListener` instead. (causes incompatibility with older versions)

## Version 1.0.0
- Release