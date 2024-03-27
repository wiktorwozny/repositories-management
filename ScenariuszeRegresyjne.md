## SCRUM-5 - Obsługa workspace'ów - Dodanie workspace'ów
### Scenariusz 1 - użytkownik chce dodać workspace
Kroki testowe:
1. Otwarcie aplikacji webowej
2. Przyciśnięcie przycisku 'Add Workspace'
3. Wpisanie detali workspace'u
4. Przyciśnięcie przycisku 'Submit'

Rezultat: Nowo stworzony workspace pojawia się w liście na ekranie
### Scenariusz 2 - użytkownik chce usunąć workspace
Kroki testowe:
1. Otwarcie aplikacji webowej
2. Przyciśnięcie przycisku edycji na danym workspace
3. Przyciśnięcie przycisku 'Delete'

Rezultat: Wybrany workspace znika z listy

### Scenariusz 3 - użytkownik chce zaktualizować workspace
Kroki testowe:
1. Otwarcie aplikacji webowej
2. Przyciśnięcie przycisku edycji na danym workspace
3. Wpisanie nowych detali workspace'u
4. Przyciśnięcie przycisku 'Submit'

Rezultat: Wybrany workspace zmienia swoje dane na nowe





## SCRUM-6 - Obsługa repozytoriów w workspace'ach - Lista repozytoriów w workspaci'e
### Scenariusz 1 - użytkownik chce dodać repozytorium
Warunek:
1. Istnieje workspace na którym wykonywane są operacje

Kroki testowe:
1. Otwarcie aplikacji webowej
2. Przyciśnięcie przycisku dodawania repozytorium (plusa)
3. Wpisanie nowych detali repozytorium
4. Przyciśnięcie przycisku 'Submit'

Rezultat: Wybrany workspace po rozwinięciu pokazuje nowe repozytorium

### Scenariusz 2 - użytkownik chce usunąć repozytorium
Warunek:
1. Istnieje workspace na którym wykonywane są operacje

Kroki testowe:
1. Otwarcie aplikacji webowej
2. Rozwinięcie odpowiedniego workspace'a
3. Przyciśnięcie przycisku edycji na danym repozytorium
4. Przyciśnięcie przycisku 'Delete'

Rezultat: Wybrane repozytorium znika z listy

### Scenariusz 3 - użytkownik chce zaktualizować repozytorium
Warunek:
1. Istnieje workspace na którym wykonywane są operacje

Kroki testowe:
1. Otwarcie aplikacji webowej
2. Rozwinięcie odpowiedniego workspace'a
3. Przyciśnięcie przycisku edycji na danym repozytorium
4. Wpisanie nowych danych repozytorium
5. Przyciśnięcie przycisku 'Submit'

Rezultat: Wybrane repozytorium zmienia swoje dane na nowe





## SCRUM-7 - Wyświetlanie PR'ów w repozytorium - Wyświetlanie PR'ów w repozytorium
### Scenariusz 1 - użytkownik chce zobaczyć PR'y
Warunek:
1. Istnieje workspace na którym wykonywane są operacje
2. W worskpace'ie jest repozytorium z poprawnym url

Kroki testowe:
1. Otwarcie aplikacji webowej
2. Rozwinięcie odpowiedniego workspacea przyciskiem strzałki
3. Rozwinięcie odpowiedniego repozytorium przyciskiem strzałki

Rezultat: Wyświetlana jest lista PR'ów z danego repozytorium

### Scenariusz 2 - użytkownik chce zobaczyć PR'y
Warunek:
1. Istnieje workspace na którym wykonywane są operacje
2. W worskpace'ie jest repozytorium z niepoprawnym url lub repozytorium nie posiada PR'ów

Kroki testowe:
1. Otwarcie aplikacji webowej
2. Rozwinięcie odpowiedniego workspacea przyciskiem strzałki
3. Rozwinięcie odpowiedniego repozytorium przyciskiem strzałki

Rezultat: Wyświetlany jest zamiast listy komunikat że nie znaleziono żadnego PR'a




## SCRUM-4 - Wyświetlanie "last commit" w repozytorium - Wyświetlanie "last commit" w repozytorium
### Scenariusz 1 - użytkownik chce zobaczyć "last commit"
Warunek:
1. Istnieje workspace na którym wykonywane są operacje
2. W worskpace'ie jest repozytorium z poprawnym url

Kroki testowe:
1. Otwarcie aplikacji webowej
2. Rozwinięcie odpowiedniego workspacea przyciskiem strzałki

Rezultat: Przy danym repozytorium widoczna jest data i godzina ostatniego commita
