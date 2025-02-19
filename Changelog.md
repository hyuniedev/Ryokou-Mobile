## [1.0.0]
## 13-12-2024  
- Create Git
- Add files ChangeLog, Readme.
- Add folders Designs, Documents, Reports.

## 15-12-2024
### Added
- Add init project.
- Add data class of models

### Fixed
- Off dynamic Color on Theme

## 16-12-2024
### Added
- Connect firebase
- Add Login with email and password (Have any bugs)
- Add Login with google account (Doing)

## 17-12-2024
### Added
- Completed Login with google account

### Fixed
- Fix Login with email and password
- Fix Client ID and SHA-1 on firebase

## 18-12-2024
### Added
- Add SignIn Scene
- Inject View with ViewModel (SignIn)

## 20-12-2024
### Added
- Add check field input when sign in
- Add sign in with email - password
- Add sign in with google account

### Fixed
- Fix alpha of background color
- Fix navigation to Home Scene (popUpTo: Delete Login scene and SignIn scene on nav stack)

## 26-12-2024
### Added
- Add Slider 
- CountDown Time

## 27-12-2024
### Added
- Add ItemTour

### Fixed
- Fix image with Coil

## 28-12-2024
### Fixed
- Type data (LocalDate -> Timestamp of Firebase)

### Added
- Load data from firebase
- display tours on homescene

## 29-12-2024
### Fixed
- fix countdown time at 24h -> out range LocalTime type

## 3-1-2025
### Added
- Change state icon favorite of item tour

## 4-1-2025
### Added
- Box navigation to Login on Home Scene

## 6-1-2025
### Fixed
- Fix top bar: hide 1 section on scroll

## 7-1-2025
### Added
- Bottom Navigation Bar on Main Scenes

### Fixed
- Show BottomBar and TopBar (redistribution, NavHost move to inner of Scafford)

## 9-1-2025
### Added
- Add Favorite scene on not logged in (1h)
- Completed Favorite scene (18h)

## 11-1-2025
### Fixed 
- signIn don't save id user
- add button log out temporary

## 12-1-2025
### Added
- update tour detail screen
- update data user on firebase when add favorite tour
- show tour schedule on tour detail screen

### Fixed
- fix re-set data when login with gg account

## 13-1-2025
### Added
- complete tour detail screen 90%
- load fullname with uid (on rate of tour detail)
- rate caculate (division rate)

## 14-1-2024
### Added
- Complete Rate UI on Tour Detail
- Save data of rate to firebase

### Fixed
- Caculate num star of rate

## 15-1-2025
### Added
- Content on Account scene

### Fixed
- List item on account scene

## 16-1-2025
### Added
- Booked tour on tour detail
- Add collection bookedTour on Firebase, connect to project on book tour

### Fixed
- Type data booked tour

## 18-1-2025
### Added
- Ui on select day start + num ticket (on book tour - Tour detail scene)
- book day on TourBooked
### Fixed
- Fix data booked tour on push to Firebase

## 19-1-2025
### Added
- Tour Pay Ui

### Fixed
- Fix Calculate price of tour division sale x2

## 22-1-2025
### Added
- add user info scene

## 24-1-2025
### Added
- Add save data user

### Fixed
- Fix ui change info user

## 29-1-2025
### Added
+ base view my tour screen

## 3-2-2025
### Fixed
- Fix ui on: Tour detail (On choose tour when to pay), Tour Pay, Account setting
- fix bug dont change state ui when save and back to display ui (User info)

## 6-2-2025
### Added
- new collection: booked tour
- show item on section my tour

### Fixed
- fix field of any tour entity

## 7-2-2025
### Added
- load booked tour of user when login and signIn
- check duplicate book tour

### Fixed
- default number of ticket on book tour
- spacer of components on ItemBookedTour
- color of card item and notifier when empty list

## 8-2-2025
### Fixed
- move function getEndDay from Tour to BookedTour (Fix get end day of booked tour)
- fix get going tour
- fix check book tour - compare date with other booked tour (completed)

## 10-2-2025
### Added
- update show schedule and info ticket
- Show info tour schedule

### Fixed
- Fix load init data booked tour on login
- Fix property name on entity user 
- split tour list into multiple small tour lists

## 11-2-2025
### Added
- Dialog system

## 13-2-2025
### Added
- Search ui
- search tour

## 14-2-2025
### Added
- add change password scene ui
- myInputTextField add keyboardType for password
- add booked Day on detail ticket
- show error dialog to notifi for user

### Fixed
- fix login with email/password is not load data (bug coroutine)

## 17-2-2025
### Fixed
- Fix textfield of password ->  hide text

## 19-2-2025
### Added
- add ticket limit
### Fixed
- transient to password
- city on tour -> list city
