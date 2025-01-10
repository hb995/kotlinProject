# Music Creation App

## Product Definition Statement

Our music creation app is a portable digital music studio that allows users to play pianos and drums on their phones or tablets. It caters to both beginners and experienced musicians, offering features like instrument sound customization, recording, and track organization. Integrated tutorials via YouTube enhance learning and make music creation accessible and enjoyable anywhere.

<div style="display: flex; flex-wrap: wrap; gap: 10px;">

  <img src="https://github.com/user-attachments/assets/98a54f1b-7eca-42d2-a770-58a4768146e9" alt="WhatsApp Image Jan 7 2025" width="200" />
  <img src="https://github.com/user-attachments/assets/a669d13d-0409-48ae-bdd8-546d683d6f33" alt="WhatsApp Image Jan 7 2025 (5)" width="200" />
  <img src="https://github.com/user-attachments/assets/127b6820-1bb9-4954-acf6-8e28aa3b97ad" alt="WhatsApp Image Jan 7 2025 (4)" width="200" />
  <img src="https://github.com/user-attachments/assets/ac1255ec-49fc-4871-b0aa-708718b62b87" alt="WhatsApp Image Jan 7 2025 (3)" width="200" />
  <img src="https://github.com/user-attachments/assets/cd83feec-4ef6-40aa-a40f-969f24829740" alt="WhatsApp Image Jan 7 2025 (2)" width="200" />
  <img src="https://github.com/user-attachments/assets/dae3d97a-c8c0-48f2-9d7d-e88896ba871b" alt="WhatsApp Image Jan 7 2025 (1)" width="200" />

</div>


## List of Implemented Features
1. **Digital Instrument Playing**: Interactive pianos and drums with multi-tap support.
2. **Music Creation Studio**: Record, save, and manage music sessions.
3. **Music Library**: Play back or delete recorded tracks.
4. **Tutorial Hub**: Embedded YouTube tutorials categorized for ease.
5. **Instrument Styles**: Choose between classic and modern designs, saved between sessions.
6. **Dark Mode**: Reduce eye strain with a darker theme.
7. **Social Sharing**: Share creations via social media.
8. **Visual Animations**: Animated feedback for instrument interaction.

## Wish List for Future Features
1. **Cloud Synchronization**: Sync recordings and settings across devices.
2. **Advanced Audio Editing Tools**: Include mixing and effects.
3. **Expanded Instrument Library**: Add more instruments.
4. **AI-Powered Music Composition Assistant**: Suggest chords and melodies.

## Self-Evaluation and Documentation
- **Grade**: A
- **Justification**: Fulfills core requirements and additional features like dark mode, social sharing, and animations. Adheres to Android UI guidelines.
- **Architecture**: Utilizes MVVM architecture for separating business logic and view management, employing DRY principles and lazy loading for efficiency.

## Class and Component Descriptions

### `AlbumsViewModel`
- **Description**: Manages album state, including loading and errors.
- **Key Functions**:
  - `fetchAlbums()`: Fetches albums from the API.
  - `getAlbumById(idAlbum: String)`: Retrieves an album by ID.

### `Album` and `AlbumX`
- **Description**: Represents albums with details like artist, sales, year, and images.

### `AudioPlayer` and `AudioPlayerInterface`
- **Description**: Handles audio playback.
- **Key Functions**:
  - `playFile(file: File)`: Plays audio.
  - `stop()`: Stops playback.

### `AudioRecorder` and `AudioRecorderInterface`
- **Description**: Manages audio recording.
- **Key Functions**:
  - `start(outputFile: File)`: Starts recording.
  - `stop()`: Stops recording.

### `DrumPad`
- **Description**: Represents a drum pad with a label and sound ID.

### `PianoKey`
- **Description**: Represents a piano key with a label, sound, and color (white/black).

### `YouTubePlayer`
- **Description**: Embeds a YouTube player for video tutorials.
- **Key Functions**: Manages video playback and lifecycle.

### `OnBoardingItems`
- **Description**: Data for onboarding screens.
- **Static Method**:
  - `getData()`: Returns onboarding data.

### `DrumScreen` and `DrumPadButton`
- **Description**: Displays drum pads and handles interaction.

### `PianoScreen` and `PianoKeyButton`
- **Description**: Displays piano keys and handles interaction.

### `RecordingScreen` and `RecordingCard`
- **Description**: Manages and displays recordings.

### `MainActivity`
- **Description**: Hosts composables and manages navigation and state.

### `AlbumScreen` and `AlbumDetails`
- **Description**: Displays album details.

## Third-Party Frameworks
- **YouTube Player Library**: Used for embedding YouTube videos.

## Conclusion

The Music Creation App is a comprehensive tool for mobile music creation, blending intuitive design with powerful features. It supports various user needs, from casual play to serious music production, and is built on modern, scalable architecture. Future expansions aim to enhance user experience further, making this app a versatile platform for musicians.

## References

1. Audio Recorder #0 : Intro. [YouTube](https://www.youtube.com/watch?v=FjFr3_MyGmA&list=PLpZQVidZ65jPz-XIHdWi1iCra8TU9h_kU&index=1)
2. THIS Is How Easily You Can Record & Play Audio In Android. [YouTube](https://www.youtube.com/watch?v=4MJFmhcONfI)
3. How to build a YouTube Player in Jetpack Compose with Kotlin. [YouTube](https://www.youtube.com/watch?v=FgAL6T_KILw)
4. Play Audio With Jetpack Compose in Android Studio. [YouTube](https://www.youtube.com/watch?v=32HjHtoyGvQ)
5. ndenicolais/Recorder. [GitHub](https://github.com/ndenicolais/Recorder)
6. Selecting piano chords with Jetpack Compose. [Medium](https://medium.com/@fluxtah/selecting-piano-chords-with-jetpack-compose-dev04-31704c7756f7)
7. Walkthrough/Onboarding/App Intro Screen Design - Jetpack Compose UI UX 2022. [YouTube](https://www.youtube.com/watch?v=eFRIyZDh514)


