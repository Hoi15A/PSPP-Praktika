:- consult("faecher.pl").
:- use_module(library(dialect/sicstus/timeout)).
:- set_test_options([silent(true)]).
:- begin_tests(voraussetzungen).
test("voraussetzungen(assit,tin1)") :- time_out(voraussetzungen(assit,tin1),1000,success).
test("voraussetzungen(tin1,assit)",[fail]) :- time_out(voraussetzungen(tin1,assit),1000,success).
:- end_tests(voraussetzungen).

:- begin_tests(waehlbar).
test("waehlbar(cgr,tin1)") :- time_out(waehlbar(cgr,tin1),1000,success).
test("waehlbar(eesy,tin1)") :- time_out(waehlbar(eesy,tin1),1000,success).
test("waehlbar(reesy,tin1)") :- time_out(waehlbar(reesy,tin1),1000,success).
test("waehlbar(tin1,cgr)",[fail]) :- time_out(waehlbar(tin1,cgr),1000,success).
:- end_tests(waehlbar).

:- begin_tests(waehlbarList).
test("waehlbarList([cgr, eesy, mc, pra, reesy, tin2],tin1)") :- time_out(waehlbarList([cgr, eesy, mc, pra, reesy, tin2],tin1),1000,success).
test("waehlbarList([cgr, eesy, pra, reesy, tin2],tin1)",[fail]) :- time_out(waehlbarList([cgr, eesy, pra, reesy, tin2],tin1),1000,success).
:- end_tests(waehlbarList).

:- begin_tests(punkte).
test("punkte([cgr, eesy, mc, pra, reesy, tin2],24)") :- time_out(punkte([cgr, eesy, mc, pra, reesy, tin2],24),1000,success).
test("punkte([cgr, eesy, mc, pra],16)") :- time_out(punkte([cgr, eesy, mc, pra],16),1000,success).
test("punkte([],0)") :- time_out(punkte([],0),1000,success).
:- end_tests(punkte).

% run_tests()
