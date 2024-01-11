**Dokument wizji projektu**

**Nazwa projektu: MoviePocket**

**Skład zespołu: Danila Prymak, Aliaksandr Trafimchyk, Anton Pazniak**

**Opiekun: Irakli Kardava**

**Data: 2023**

**1. Executive summary (max. 150 słów)**

Naszą główną ideą jest stworzenie miejsca, gdzie ludzie będą mogli wyrazić swoją miłość do filmów
oraz dzielić się tą
miłością z innymi użytkownikami. Nasz serwis pozwoli ludziom śledzić ich historię oglądanych filmów,
tworzyć własne
listy ulubionych filmów według ich preferencji i udostępniać je innym, aby mogli odkrywać różne
gusta ludzi w filmach.
Ważne, aby powiedzieć, że użytkownicy nie będą mogli oglądać filmów na naszej platformie, co naszym
zdaniem pomaga
utrzymać naszą platformę tak Towarzyską, jak to tylko możliwe, co oznacza zmniejszenie rozpraszania
uwagi od innych
funkcji, które nie są z tym połączone. Planujemy również dodać moduł review. Ludzie będą mogli
ocenić film i zostawić
krótką recenzję na jego temat. Grupą docelową naszej strony internetowej będą osoby, które chcą
połączyć się ze
społecznością wokół swoich ulubionych filmów z różnych gatunków i mieć narzędzie, które pomoże
zorganizować własną
kolekcję filmów w łatwy sposób. Generalne ryzyko związane z wdrożeniem projektu które nasz zespół
wykrył jest to, że
publiczność nie jest przekonana do wybrania naszej usługi zamiast zwykłych notatek, baz danych
filmów typu IMDB lub
platform streamingowych. Doszliśmy jednak do wniosku, że nasz pomysł jest na tyle wyjątkowy, że
przyciąga ludzi ze
względu na skupienie się na funkcjonalności związanej ze społeczeństwem (możliwość dzielenia się
swoimi listami filmów z
innymi użytkownikami na ogólnej stronie serwisu) oraz poczucie kolekcji (własne spersonalizowane
listy), co robi taki
minimalizm unikalnym.

**2. Cel i grupa docelowa (min. 150 słów)**

- Problem, który znaleźliśmy, wynikał z niskiego zainteresowania społecznością korzystającej z
  innych takich produktów.
  Większość z tych web-aplikacji to po prostu baza danych, w której można znaleźć film, który chcesz
  obejrzeć, a także
  średnią ocenę wśród użytkowników. Chcemy dać więcej swobody klientom i społeczności, która będzie
  korzystać z naszego
  produktu.

- Grupą docelową będą osoby, które są wielkimi fanami branży kinowej. Chcielibyśmy zbudować nową
  społeczność fanów
  twórczości w dziedzinie kinematografii, seriali, kreskówek i anime. Niezależnie od preferencji
  użytkowników, chcemy
  stworzyć miejsce, w którym każdy zainteresowany będzie mógł znaleźć znajomych o podobnych
  preferencjach, zrecenzować
  obejrzane filmy lub po prostu zbudować kolekcję obejrzanych filmów.

- Projekt nie ma konkretnego klienta. Klienci końcowi naszego produktu będą poszukiwani poprzez
  różne sieci
  społecznościowe, dystrybucję wśród znajomych i ewentualną reklamę.

- Głównym produktem projektu będzie aplikacja internetowa. Wybraliśmy to rozwiązanie, ponieważ
  pozwoli naszym klientom
  szybko i łatwo korzystać z naszego produktu bez konieczności korzystania z zewnętrznych aplikacji.

- Główną wartością naszego produktu będą fani twórczości związanej z kinem, kreskówkami, serialami i
  anime. Chcemy
  zebrać wszystkie takie osoby w jednym miejscu, w którym będzie im wygodnie spędzać czas, szukać
  nowych przyjaciół, a
  także odkrywać nowe kino. Umożliwimy naszym klientom pełne zarządzanie kolekcją ich filmów, tagami
  do tych filmów,
  własnymi recenzjami, a także dyskusjami na temat filmu lub czyjejś recenzji. Damy użytkownikom
  możliwość stworzenia
  własnego feedu z filmów, a także współuczestnictwa w tworzeniu opisów i gatunków do wydanych
  filmów.

**3. Rynek (min. 3 konkurencyjne produkty)**

- **IMDb**

**Zalety:** Świetny produkt, który ma ogromną rzeszę fanów, a także dużą liczbę featurów.

**Wady:** Opiera się bardziej na informacjach o filmie niż na społeczności fanów segmentu. Miejsce,
w którym można
zostawić recenzję jest przestarzałe. Nie można komentować recenzji innych osób. Strony użytkowników
wyglądają nudno.
Brak możliwości interakcji z innymi użytkownikami.

- **MovieChat**

**Zalety:** Możliwość tworzenia threadów i dyskusować w nich o filmach.

**Wady:** Zły wygląd aplikacji. Brak własnego systemu oceny filmów. Strona użytkownika nie pokazuje
nic poza utworzonymi
threadami i odpowiedziami w nich.

- **TV Maze**

**Zalety:** Duża ilość featurów do użytku własnego typu: episode list, episode calendar, schedule i
td.

**Wady:** Całkowity brak systemu interakcji z klientem. Ten produkt nie pozwala klientom na wzajemną
interakcję.
Zapewnia tylko rozwiązanie dla ich własnych zadań pojedynczego użytkownika.

**4. Opis produktu (min. 3 moduły/epiki)**

**Typ użytkownika:** administrator, użytkownik

**Zarządzanie i przetwarzanie danych:** Dane będą przechowywane w bazie danych MySQL przy użyciu
obiektowo-relacyjnego
mapowania. RESTful API zostanie użyty do pobrania danych wejściowych użytkownika i podania danych
wyjściowych.

**Integracja z systemami zewnętrznymi:** Movie Database API

**Integracja Movie DB:**

Filmy będą pobierane przez API bazy danych filmów i przechowywane w tabeli filmów w bazie danych.
Jeśli użytkownik chce
dodać film do własnej listy, relacja pojawi się w bazie danych. Użytkownicy mogą mieć kilka filmów
na swoich listach.

**Własne listy filmów:**

Pojawi się panel użytkownika, do którego mogą uzyskać dostęp Tylko zalogowani użytkownicy.
Użytkownicy mogą tworzyć
własne listy z filmami a także dodawać filmy do już istniejących list, które wybrali zgodnie ze
swoimi preferencjami.
Użytkownicy mogą mieć wiele różnych list, na przykład "ulubione Western lata 90." lub coś bardziej
wyjątkowego, jak "
filmy, które sprawiają, że płaczę". Listy mogą być prywatne lub publiczne. Jeśli są publiczne, inni
mogą zobaczyć listy
na stronie głównej lub w profilu użytkownika.

**Moduł review:**

`             `Recenzje będą mogli zostawić tylko zalogowane użytkownicy.

Użytkownicy mogą zostawić krótką recenzję dla każdego filmu. Na każdej stronie filmu będą recenzje i
oceny od różnych
użytkowników. Oceny mogą wynosić od 0 do 5.

**Profil filmu:**

Każdy film będzie miał własną stronę z krótkimi informacjami na jego temat: aktorzy, czas trwania
itp. Również recenzję
z “Moduł review” będą widoczne na stronie, jeśli są pozostawione przez użytkowników. Średnia ocena
filmu zostanie
obliczona na podstawie oceny filmu od wszystkich użytkowników na tym filmie.

**5. Zakres i ograniczenia**

**Skład zespołu:**

Aliaksandr Trafimchyk – Frontend, ReactJS

Danila Prymak – Backend, testing

Anton Pazniak – Frontend, ReactJS

**Technologie:**

1. Frontend:
    1. ` `JS
    1. ` `HTML
    1. ` `CSS
    1. ` `ReactJS
1. Backend:
    1. Java
    1. Spring
1. Database
    1. MySQL

**Kamienie milowe:**

Semestr 1:

\- zakończenie integracji bazy danych filmów

\- Backend API prototyp

\- Budowanie pierwzego prototypu front wersji produktu

Semestr 2:

\- zakończony User interface

\- beta versja produktu działająca na production serwerze oraz otwarta dla zewnętrznych testerów 

