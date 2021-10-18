mann(ali).
mann(albert).
mann(adolf).
mann(alex).
mann(bruno).
mann(beat).
mann(benno).
mann(bjoern).
mann(caspar).
mann(daniel).

frau(anna).
frau(anita).
frau(agatha).
frau(alessia).
frau(bea).
frau(berta).
frau(claudia).
frau(carla).
frau(deborah).
frau(daisy).

vater(ali, bruno).
vater(ali, beat).
vater(albert, bea).
vater(adolf, benno).
vater(adolf, berta).
vater(alex, bjoern).
vater(beat, claudia).
vater(bjoern, caspar).
vater(bjoern, carla).
vater(caspar, daniel).
vater(caspar, deborah).
vater(caspar, daisy).

mutter(anna, bruno).
mutter(anna, beat).
mutter(anita, bea).
mutter(agatha, benno).
mutter(agatha, berta).
mutter(alessia, bjoern).
mutter(bea, claudia).
mutter(berta, caspar).
mutter(berta, carla).
mutter(claudia, daniel).
mutter(claudia, deborah).
mutter(claudia, daisy).

% elternteil
elternteil(X,Y) :- mutter(X,Y);vater(X,Y).

% geschwister
geschwister(X,Y) :- elternteil(Z,X),elternteil(Z,Y),not(X = Y).

% Grossmutter abfragen 
oma(X,Y) :- mutter(X,Z),elternteil(Z,Y).

% Grossvater abfragen
opa(X,Y) :- vater(X,Z),elternteil(Z,Y).

% Tante abfragen
tante(X,Y) :- schwester(X,Z),elternteil(Z,Y).

% Onkel abfragen
onkel(X,Y) :- bruder(X,Z),elternteil(Z,Y).

% Urgrossmutter abfragen
uroma(X,Y) :- oma(X,Z),elternteil(Z,Y).

% Urgrossvater abfragen
uropa(X,Y) :- opa(X,Z),elternteil(Z,Y).

% Schwester abfragen
schwester(X,Y) :- frau(X),elternteil(Z,X),elternteil(Z,Y).

% Bruder abfragen
bruder(X,Y) :- mann(X),elternteil(Z,X),elternteil(Z,Y).

% Sohn abfragen
sohn(X,Y) :- mann(X),elternteil(Y,X).

% Tochter abfragen
tochter(X,Y) :- frau(X),elternteil(Y,X).

% Vorfahre abfragen
vorfahre(X,Y) :- elternteil(X,Y);oma(X,Y);opa(X,Y);uroma(X,Y);uropa(X,Y).

