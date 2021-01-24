Savu Adrian-Mihai CD321

Principalele clase ale acestei implementari sunt Consumer, Distribuitor si
Producer,fiecare avand campuri specifice pentru aceste obiecte. Campurile sunt 
de doua tipuri: cele necesare pentru parsarea informatiilor din fisierul dat ca 
input si cele care retin informatii relevante pentru rundele din simulare.
Cele trei clase interactioneaza in mod constant intre ele, prin intermediul 
clasei Distributor: fiecare distribuitor are o lista de consumatori curenti sau
care au de platit datorii si fiecare consumator are cate un camp pentru 
distribuitorul curent si cel la care are datorii (daca este cazul, campurile 
pot sa aiba acelasi distribuitor). Consumatorul interactioneaza cu baza de date
pentru alegerea unui nou contract. Distribuitorul are o lista de Producers de 
la care isi ia energia in runda curenta, in functie de strategia aleasa. 
Producatorul tine evidenta distribuitorilor care si au luat energia in runda 
curenta si retine intr-un camp de tip HashMap o lista de Distributors care 
corespunde fiecarei luni din simulare.

Toate formulele necesare pentru calcularea contractelor/ a noului buget sunt 
implementate ca metode in clasa Formulas, ele sunt folosite in majoritatea
metodelor din clasele Distribuitor si Client si pentru sortarea 
distribuitorilor din clasa DistributorSorts, sortDistribuitorsByContractPrice.
Pentru alegerea producatorului de catre Distribuitor, am realizat in clasa
ProducerSorts metode pentru sortarea producatorilor din baza de date 
in functie de strategia distribuitorului

In ceea ce priveste parsarea informatiilor din input, fisierele au fost
impartite in 2: inputul care este luat la inceput (numarul de runde, 
consumatorii, distribuitorii si producatorii cu datele lor) si update-urile 
lunare (consumatorii adaugati in joc si schimbarile de costuri ale 
distribuitorilor).
Pentru prima parte am folosit clasa Input care are campurile mentionate mai
sus si o lista de update-uri si pentru cea de a 2 a o clasa MonthlyUpdates
care contine listele de clienti noi si cost changes. Toate sunt folosite 
in clasa InputLoader.

Am folosit singleton pentru clasele FactoryConsumer si FactoryDistributor,
patternul Factory pentru a genera Consumer si Distributor si Strategy pentru
metoda de alegere a producatorilor (behaviour-ul este dat de strategia
distribuitorului).
