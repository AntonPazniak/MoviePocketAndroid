**Dokument wymagań projektowych**

**Nazwa projektu: MoviePocket**

**Autorzy: Danila Prymak, Aliaksandr Trafimchyk, Anton Pazniak**

**Data: 01.12.23**

**0. Wersje dokumentu**

ver. 0 - 27.04.23

ver 0.1 - 18.05.23

\- Volha Andrawa została wrócona do zespołu

\- dostosowanie dokumentu do uwag komisji

ver 0.2 - 22.10.23

-usunięcie Volhi Andrava z zespołu

\- dodanie do listy wymagań funkc. oraz kryteriów akceptacji na 2 semestr aplikacji mobilnej

\- małą zmiana listy wymagań funkc. oraz kryteriów akceptacji na 2 semestr

\- zmiana licencji na proprietary(zamknięty repo)

**1. Elementy składowe projektu (produkty projektu)**

Naszą główną ideą jest stworzenie miejsca, gdzie ludzie będą mogli wyrazić swoją miłość do filmów
oraz dzielić się tą miłością z innymi użytkownikami. Nasz serwis pozwoli ludziom śledzić ich
historię oglądanych filmów, tworzyć własne listy ulubionych filmów według ich preferencji i
udostępniać je innym, aby mogli odkrywać różne gusta ludzi w filmach. Ważne, aby powiedzieć, że
użytkownicy nie będą mogli oglądać filmów na naszej platformie, co naszym zdaniem pomaga utrzymać
naszą platformę tak Towarzyską, jak to tylko możliwe, co oznacza zmniejszenie rozpraszania uwagi od
innych funkcji, które nie są z tym połączone. Planujemy również dodać moduł review. Ludzie będą
mogli ocenić film i zostawić krótką recenzję na jego temat. Grupą docelową naszej strony
internetowej będą osoby, które chcą połączyć się ze społecznością wokół swoich ulubionych filmów z
różnych gatunków i mieć narzędzie, które pomoże zorganizować własną kolekcję filmów w łatwy sposób.

Elementy programistyczne:

- Back-end: Java/Spring
- Front-end: JS/React
- Mobilna aplikacja:Java, Android 10+
- Tests: JEST for React, JUnit 4 for Spring
- ORM: Hibernate
- Baza danych
    - Mysql

Elementy nieprogramistyczne:

- Dokumentacja wizji projektu
- Dokumentacja wymagań projektowych
- Dokumentacja dla użytkownika
- Dokumentacja deweloperska
- Licencja i podział praw własności
- Wymagania funkcjonalne
- UI design (Figma)

**2. Granice projektu**

- **Brak możliwości oglądania filmów**

Z powodu naruszenia praw własności i trudności z osadzeniem funkcji oglądania nie jest możliwe
obejrzenie samego filmu / pokazu na stronie internetowej. Z drugiej strony to pomoże skoncentrować
się na głównym celu usługi naszego serwisu, którym jest udostępnianie kolekcji filmów innym osobom,
a także koncentrowanie na aspektach społecznych naszego serwisu.

- **Połączenie z API TMDB(zewnętrzne API)**

Niektóre kluczowe funkcje, takie jak baza filmów i programów telewizyjnych, będą pobierane za pomocą
odwołania do API TMDB, co sprawia, że nasza usługa jest w jakiś sposób niezawodna od usługi TMDB.
Jeśli wystąpią problemy z TMDB, to spowoduje to problemy z MoviePocket.

- **Język interfejsu**

Głównym językiem interfejsu naszego serwisu jest angielski ze względu na fakt, że dane filmowe będą
pobierane z angielskiego TMDb.

- **Aplikacja mobilna działa tylko na Android 10+**

Serwis będzie miał aplikacje mobilną dostępną do instalowania w Play Markiet.

- **Сzęsć funkcyjności będzie przedstawiona tylko w aplikacji mobilnej**

Ze względu na rozmiar zespołu deweloperów, określoność casu oraz adaptację do chęci grupy docelowej
cześć funkcyjności będzie przedstawiona tylko w aplikacji mobilnej

**3. Lista wymagań funkcjonalnych**

**Użytkownik**

1. Uwierzytelnienie:
    1. Rejestracja: Odwiedzający będzie mógł stworzyć swoje konto (potrzebna poczta).
    1. Logowanie: Odwiedzający będzie mógł zalogować się do swojego konta.
    1. Wylogowanie: Użytkownik będzie mógł wylogować się.
    1. Zmiana hasła: Użytkownik będzie mógł zmienić hasło.
    1. Zapomnienie hasła: Użytkownik będzie mógł odzyskać konto, jeśli zapomniał hasła potrzebna
       poczta).
    1. Możliwość otrzymania maila z potwierdzeniem na email podany użytkownikiem
1. Zmiana informacji o siebie na swojej stronie: Użytkownik będzie mógł zmienić informację o siebie
   na swojej stronie (z pewnymi ograniczeniami).
1. Zmiana Avataru
1. Usunięcie konta: Użytkownik będzie mógł usunąć swoje konto.

**Lista Filmów**

1. Utworzenie listy: każdy użytkownik może utworzyć więcej niż jedną listę z krótkim opisem i dodać
   tam filmy (wizualna część listy jest opcjonalna).
1. Dodawanie filmu do listy: użytkownik może dodawać filmy do listy.
1. Edycja listy: użytkownik może dodawać lub usuwać filmy do już istniejącej listy.
1. Ocenianie listy: użytkownik może ocenić listę innych użytkowników (like albo dislike)
1. Tworzenie listy ulubionych filmów: każdy użytkownik ma domyślną listę
   ulubionych/toWatch/oglądane, na której może dodawać filmy bezpośrednio ze strony filmu.

**Film**

1. Przejście do strony filmu: użytkownik może przejść do strony każdego filmu/ programu
   telewizyjnego.
1. Dodawanie do ulubionych/toWatch/oglądane: użytkownik może dodać filmy do domyślnych listów.
1. Ocenianie: użytkownik może ocenić film w skali 1-10.
1. Zostawienie recenzji: użytkownik może zostawić recenzję na stronie filmu, która jest widoczna dla
   innych.(**tylko w aplikacji mobilnej**)
1. Ocenianie recenzji: użytkownik może ocenić recenzję (like albo dislike).
1. Zwiastun filmu: użytkownik mogą oglądać zwiastun filmu.
1. Oznacz jako obserwowany: użytkownik może odznaczyć film jako obserwowany, który zostanie zapisany
   na liście obserwowanych użytkownika.
1. Przejście do strony użytkownika twórcy recenzji: użytkownik może przejść na stronę drugiego
   użytkownika, który zostawił swoją recenzję.
1. Przejście do strony aktora lub reżysera (jeśli istnieje): użytkownik może przejść na stronę
   aktora lub reżysera ze strony filmu.

**Strona aktora, reżysera**

1. Obejrzenie listy filmów, w których aktor (reżyser) uczestniczył: użytkownik może wejść do tej
   listy i przejść na stronę filmu.

**Post(tylko w aplikacji mobilnej)**

1. Tworzenie posta: użytkownik może tworzyć posty, w których mogą udostępniać jedną ze swoich list i
   krótki opis, dodawać swoje stworzone listy filmy jak i same filmy.
1. Przeczytanie posta: odwiedzający może wyświetlić post, żeby go przeczytać.
1. Edytowanie posta: użytkownik może edytować swoje posty, które mają status wersji roboczej.
1. Usuwanie posta: administrator może usuwać dowolne posty.
1. Like posta: użytkownik może dodać like do dowolnego z postów i zobaczyć liczbę like w poście.
1. Ocenianie posta:  użytkownik może ocenić post (like albo dislike)
1. Zostawienie recenzji: użytkownik może zostawić recenzję dla postu, która jest widoczna dla
   innych.

**Strona główna**

1. Wyszukanie filmu: użytkownik może wyszukiwać filmy według tytułu na pasku wyszukiwania.
1. Obejrzenie trendów: użytkownik może oglądać listę popularnch filmów.
1. Listy sezonowe od zespołu MoviePocket( tylko w aplikacji mobilnej): stworzone listy od
   administratora dopasowane do sezonu( np. Christmas movies)

` `**Strona wyszukiwania** (tylko w aplikacji mobilnej):

1. Pouplarne filmy
1. Popularne aktorzy
1. Popularne serialy

**Sledzienie filmu:**

1\.Zaznaczyć filmy które jeszcze nie są w kinoteatrach dla otrzymania powiadomienia e-mail o ich
dostępność

**4. Lista wymagań niefunkcjonalnych**

1. Bezpieczeństwo. Użytkownicy oraz odwiedzające nie mogę korzystać z niczego oprócz powyżej
   wymienionych funkcjonalności systemu.
1. Bezpieczeństwo zewnętrzne. System powinien być zabezpieczony przed różnymi rodzajami atak oraz
   zabezpieczona ochrona danych użytkownika.
1. Możliwość otrzymywania informacji od stron trzecich. Na przykład odnośnie reklamy albo dotyczące
   praw własności.
1. Dla korzystania z urządzeń mobilnych serwis będzie posiadał aplikację mobilną Android.

**5. Kryteria akceptacji projektu dla I semestru prac**

- **Wymagane**
    - Back-end API prototyp
    - UI prototyp dla zakończonych modułów
    - Uwierzytelnienie użytkownika z możliwością potwierdzenia email
    - Moduł strony głównej
    - Moduł Oceniania filmów
    - Moduł Filmu (strona filmów z informacją)
    - integracja bazy danych, odczyt, zapis i odpytywanie danych dla każdego ukończonego komponentu
    - projektowanie i implementacja modułu bazy danych Filmów
    - Funkcje edycji i usuwania konta użytkownika
- **Oczekiwane (Może zostać przeniesiony na drugi semestr lub pominięty)**
    - Funkcjonalność dodawania do ulubionych filmów
    - Moduł Recenzji filmów
    - Tworzenie konta użytkownika u zewnętrznych dostawców OAuth
    - Unit-testy

**6. Mierzalne wskaźniki wdrożeniowe**

- **Serwis będzie publicznie dostępny na domenie, aplikacja mobilna – w Play Market**
- Liczba użytkowników przekroczy 10 osób
- Produkt będzie zawierał system zbierania filmów w listy i system udostępniania listów pomiędzy
  użytkownikami. Produkt będzie używany przez nasi wewnętrzni testerzy.
- System będzie skończony ze względu na UI-design
- Każdy użytkownik będzie miał min. 2 listy filmów oraz 2 posta
- Min. Na 5 filmach będą widoczne recenzji (min. 2)

**7. Kryteria akceptacji projektu dla II semestru prac**

- **Wymagane**
    - Funkcjonalność postu(dodawanie, usuwanie, edycja, like)
    - Zakończona funkcjonalność listów filmów
    - Funkcjonalność modułu Review
    - Gotowy interfejs UI dla każdego modułu
    - Udostępnienie serwisu na publicznym domenie
    - Pełna wersja robocza strony internetowej
    - Avatary użytkownika
    - Funkcjonalność dodawania do ulubionych filmów
    - Zakończona aplikacja mobilna Android
- **Oczekiwane (Te funkcje mogą być dodawane lub nie))**
    - Unit-testy
    - Funkcjonalność związana ze stronami aktora, reżysera
    - Funkcjonalność wyszukiwania użytkowników, postów
    - Administracja kontenta oraz procesów związanych z działalnością serwisu
    - Implementacja automatyzacji procesów (DevOps)

**8. Organizacja pracy zespołu**

- Danila Prymak
    - Backend, tester
- Aliaksandr Trafimchyk
    - UI design, frontend, tester
- Anton Pazniak
    - Backend, tester

Każda rola będzie zawierać następujące obowiązki:

- Backend developer
    - tworzy, utrzymuje i nadzoruje
- baza danych
- Punkty końcowe REST API
- Logika biznesowa
- Autoryzacja
- odpowiada za Continuous Integration and Delivery
- Frontend developer
    - implementuje projekt stworzony przez projektantów UI
- Angażuje się w dyskurs z projektantami UI, aby osiągnąć równowagę między łatwością wdrożenia,
  atrakcyjności wizualnej i dostępności
- UI designer
- tworzy projekt dla każdej strony / widoku aplikacji
- angażuje się w dyskurs z programistami frontend, jak zdefiniowano powyżej
- Tester
    - Tworzy unit testy
        - Frontend tester pisze testy, które weryfikują niezmienniki danego przepływu UX
        - Backend tester pisze testy, które sprawdzają integralność danych i logikę biznesową
- tworzy testy End-to-End
    - Razem tworzą testy, które integrują dwie domeny (backend i frontend) poprzez emulację
      rzeczywistego użytkownika klikając przyciski

**Metoda pracy**

Zastosujemy metodę hybrydową, aby spełnić swoje specyficzne wymagania, wybierając elementy z różnych
metod.

Użyjemy tak zwanej mieszanki **metodologii Scrum i Kanban**, aby uzyskać potrzebne zalety od obu z
nich i zachować elastyczny przepływ pracy w ramach projektu.

Będziemy mieli 2 główne terminy dostarczenia Oprogramowania (Semestr 1 i 2), każdy z których będzie
podzielona na min. **2 przyrosty funkcjalności (sprint).**

Podobnie jak w Scrum będziemy mieli predefiniowaną listę funkcjonalności (wymagane kryteria
akceptacji projektu na semestr), które planujemy wdrożyć dla każdego sprintu z priorytetem
odpowiednio do znaczenia (wymaganego i oczekiwanego) i podzielonego na historie użytkowników z
podzadaniami do zrobienia, ale użyjemy Kanban Just-In-Time aproach, aby zwiększyć elastyczność w
zakresie zmiany priorytetów lub dodać inne zadania zgodnie z aktualną sytuacją w sprincie.

**Nie będziemy mieć Scrum mastera**, ponieważ każdy członek zespołu będzie mógł wybierać zadania i
zmieniać przepływ pracy, który jest omawiany i zatwierdzany przez wszystkich na **cotygodniowych
spotkaniach** (stały kontakt między członkami będzie utrzymywany przy użyciu platform
komunikacyjnych jako Teams lub Telegram).

Jako narzędzie do śledzenia zadań użyjemy **tablicy Kanban z zadaniami To-do, In progress i Done**
dla każdego sprintu z omawianym WIP (Work in progress) dozwolonym dla członka zespołu z **nie więcej
niż 5** zadania z User story / Bug fix / design/ innych zadań).

Wstępnie zdefiniowaliśmy w Jira typy zadań, takie jak **Epic**, **User story** (moduły z wartością
business dla projektu i dostosowany do jednego z Epic), **Task** (Brak wartości business np. "Dodaj
komentarze do kodu"), **Bugs** (połączone lub wymienne z Github issues)** i **Subtasks** zarówno dla
stories, jak i zadań.

**Cykl życia zadania:**

- Najpierw pomysł jest omawiany wśród członków zespołu, a następnie dodawany do Jiry do kolumny TODO
  jako historia użytkownika lub typ zadania z opisem i przypisaną do niego osobą (może to być więcej
  niż jedna osoba).
- Zadanie jest wykonywane w oddzielnym brunchu (czasami kilka zadań w jednym brunchu, jeśli jest to
  drobna rzecz) i scalane z przejrzanym przez wszystkich członków pull requestem do dev brunchu.
    - w przypadku znalezienia błędów podczas przeglądu tworzone jest osobne zgłoszenie w GitHub lub
      zadanie błędu w Jira.
- Po pozytywnej recenzji i poprawkach błędów w dev brunchu jest on łączony z pull requestem do main
  jako przyrost i koniec sprintu.

**Narzędzia wsparcia projektu**

- Github
    - Kod źródłowy i hosting kontroli wersji
- Microsoft Teams
    - Platforma dla komunikacji, spotkań i współpracy
- Microsoft Word
    - Współpracujący edytor online do pisania dokumentacji
- Jira
    - Narzędzie dla śledzenia workflow projektu oraz przydzielania zadań członkom zespołu
- Figma
    - Narzędzie dla tworzenia prototypu UI-design

**9. Ryzyka projektowe**

- Ten projekt ma duże ryzyko, że nie będzie miał duże zainteresowanie ze strony szerokiej
  publiczności, ponieważ istnieje wiele analogicznych serwisów.
- Problem z bazą danych - ze względu na ograniczenia naszych zasobów nie mamy możliwości
  samodzielnie tworzyć bazy danych z filmami. Dlatego będziemy musieli używać gotowych baz danych
  firm zewnętrznych.
- Problemy z prawami autorskimi i przepisami prawnymi mogą prowadzić do poważnych problemów
  prawnych, jeśli na stronie zostaną wykorzystane nielegalnie chronione treści.
- Koszty tworzenia i utrzymywania strony mogą być bardzo wysokie, jest to duża strona z dużą ilością
  informacji. Koszty te obejmują hosting, utrzymanie strony i aktualizację treści.
- Zapewnienie bezpieczeństwa użytkowników i ich danych osobowych jest jednym z najważniejszych
  aspektów. Należy zastosować odpowiednich narzędzi i praktyki, takich jak szyfrowanie danych i
  stosowanie bezpiecznych protokołów komunikacyjnych, aby chronić użytkowników przed kradzieżą
  danych i innymi zagrożeniami.
- Zapewnienie wystarczającej przepustowości i szybkości ładowania strony. Konieczne jest zapewnienie
  wystarczającej mocy obliczeniowej i przepustowości serwera oraz optymalizacja kodu strony, aby
  zminimalizować czas ładowania.

**10. Kamienie milowe**

**I semestr:**

- Funkcjonujący system rejestracji/logowania
- System komunikacji pomiędzy back-end i front-end
- Pierwszy interfejs stworzony w React
- Doprecyzowanie dokumentu wymagań
- Funkcjonująca baza danych wraz z możliwością dodawania i edytowania użytkowników, filmów

- Zakończona dokumentacja wizji projektu oraz wymagań
- Ukończony prototyp UI-design/figma oraz bazy danych
- Moduł uwierzytelnienia użytkownika
- Implementacja strony głównej serwisu oraz strony filmu
- Moduł Recenzyj filmów
- Moduł Oceniania filmów
- Ukończony prototyp backend REST API połonczony React frontendem odpowiadający kryterium akceptacji
  dla 1go semestru

**II semestr:**

- Wdrażanie aplikacji na publicznym domenie
- Dostęp do aplikacji mobilnej w sklepie Play market
- Skończony moduł listy filmów
- Skończony moduł postów
- Skończony moduł dodawania do ulubionych
- Ukończony interfejs użytkownika dla każdego modułu z implementacją ciemnego motywu
- Wykonane wszystkie testy
- Zakończona aplikacja mobilna 


