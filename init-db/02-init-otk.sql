--
-- PostgreSQL database dump
--

-- Dumped from database version 14.15 (Ubuntu 14.15-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.15 (Ubuntu 14.15-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: esdp; Type: SCHEMA; Schema: -; Owner: olegmm
--

CREATE SCHEMA esdp;


ALTER SCHEMA esdp OWNER TO otkreader;

--
-- Name: ost22; Type: SCHEMA; Schema: -; Owner: olegmm
--

CREATE SCHEMA ost22;


ALTER SCHEMA ost22 OWNER TO otkreader;

--
-- Name: thread_m; Type: SCHEMA; Schema: -; Owner: olegmm
--

CREATE SCHEMA thread_m;


ALTER SCHEMA thread_m OWNER TO otkreader;

--
-- Name: element; Type: TYPE; Schema: esdp; Owner: olegmm
--

CREATE TYPE esdp.element AS ENUM (
    'hole',
    'shaft'
);


ALTER TYPE esdp.element OWNER TO otkreader;

--
-- Name: pitch_ranges; Type: TYPE; Schema: public; Owner: olegmm
--

CREATE TYPE public.pitch_ranges AS ENUM (
    's1e1_4',
    's1_4e2_8',
    's2_8e5_6',
    's5_6e11_2',
    's11_2e22_4',
    's22_4e45',
    's45e90'
);


ALTER TYPE public.pitch_ranges OWNER TO otkreader;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: basic_tolerance; Type: TABLE; Schema: esdp; Owner: olegmm
--

CREATE TABLE esdp.basic_tolerance (
    bas_tol character varying NOT NULL,
    bas_tol_id integer NOT NULL
);


ALTER TABLE esdp.basic_tolerance OWNER TO otkreader;

--
-- Name: basic_tolerance_bas_tol_id_seq; Type: SEQUENCE; Schema: esdp; Owner: olegmm
--

ALTER TABLE esdp.basic_tolerance ALTER COLUMN bas_tol_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME esdp.basic_tolerance_bas_tol_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: deviation_code; Type: TABLE; Schema: esdp; Owner: olegmm
--

CREATE TABLE esdp.deviation_code (
    dev_code integer NOT NULL
);


ALTER TABLE esdp.deviation_code OWNER TO otkreader;

--
-- Name: element_type; Type: TABLE; Schema: esdp; Owner: olegmm
--

CREATE TABLE esdp.element_type (
    el_type esdp.element NOT NULL
);


ALTER TABLE esdp.element_type OWNER TO otkreader;

--
-- Name: main_reference; Type: TABLE; Schema: esdp; Owner: olegmm
--

CREATE TABLE esdp.main_reference (
    rtt_id uuid NOT NULL,
    dev_code integer NOT NULL,
    es integer NOT NULL,
    ei integer NOT NULL
);


ALTER TABLE esdp.main_reference OWNER TO otkreader;

--
-- Name: size_range; Type: TABLE; Schema: esdp; Owner: olegmm
--

CREATE TABLE esdp.size_range (
    range_id integer NOT NULL,
    s_range numrange NOT NULL
);


ALTER TABLE esdp.size_range OWNER TO otkreader;

--
-- Name: range_range_id_seq; Type: SEQUENCE; Schema: esdp; Owner: olegmm
--

ALTER TABLE esdp.size_range ALTER COLUMN range_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME esdp.range_range_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: range_tolerance_by_type; Type: TABLE; Schema: esdp; Owner: olegmm
--

CREATE TABLE esdp.range_tolerance_by_type (
    rtt_id uuid NOT NULL,
    el_type esdp.element NOT NULL,
    bas_tol_id integer NOT NULL,
    range_id integer NOT NULL
);


ALTER TABLE esdp.range_tolerance_by_type OWNER TO otkreader;

--
-- Name: def_deviances; Type: TABLE; Schema: ost22; Owner: olegmm
--

CREATE TABLE ost22.def_deviances (
    nom_dim_range numrange NOT NULL,
    hole numeric(5,3),
    shaft numeric(5,3),
    quasi_hole numeric(5,3),
    quasi_shaft numeric(5,3)
);


ALTER TABLE ost22.def_deviances OWNER TO otkreader;

--
-- Name: undef_deviances; Type: TABLE; Schema: ost22; Owner: olegmm
--

CREATE TABLE ost22.undef_deviances (
    dim_range numrange NOT NULL,
    deviance numeric(4,3)
);


ALTER TABLE ost22.undef_deviances OWNER TO otkreader;

--
-- Name: basic_deviances; Type: TABLE; Schema: thread_m; Owner: olegmm
--

CREATE TABLE thread_m.basic_deviances (
    dev_letter character varying NOT NULL,
    deviances_arr numeric(4,3)[]
);


ALTER TABLE thread_m.basic_deviances OWNER TO otkreader;

--
-- Name: nom_diam_tolerances; Type: TABLE; Schema: thread_m; Owner: olegmm
--

CREATE TABLE thread_m.nom_diam_tolerances (
    tolerance_number integer NOT NULL,
    nom_diam_tolerance numeric(5,3)[]
);


ALTER TABLE thread_m.nom_diam_tolerances OWNER TO otkreader;

--
-- Name: nom_diams; Type: TABLE; Schema: thread_m; Owner: olegmm
--

CREATE TABLE thread_m.nom_diams (
    nom_diam numeric(6,3) NOT NULL,
    pitch_default numeric(6,3)
);


ALTER TABLE thread_m.nom_diams OWNER TO otkreader;

--
-- Name: pitch_diam_tolerances; Type: TABLE; Schema: thread_m; Owner: olegmm
--

CREATE TABLE thread_m.pitch_diam_tolerances (
    nom_diam_range numrange NOT NULL,
    pitch numeric(4,3) NOT NULL,
    "3" integer,
    "4" integer,
    "5" integer,
    "6" integer,
    "7" integer,
    "8" integer,
    "9" integer,
    "10" integer
);


ALTER TABLE thread_m.pitch_diam_tolerances OWNER TO otkreader;

--
-- Name: pitches; Type: TABLE; Schema: thread_m; Owner: olegmm
--

CREATE TABLE thread_m.pitches (
    pitch_id integer NOT NULL,
    pitch numeric(6,3)
);


ALTER TABLE thread_m.pitches OWNER TO otkreader;

--
-- Data for Name: basic_tolerance; Type: TABLE DATA; Schema: esdp; Owner: olegmm
--

COPY esdp.basic_tolerance (bas_tol, bas_tol_id) FROM stdin;
a	1
b	2
c	3
cd	4
d	5
e	6
ef	7
f	8
fg	9
g	10
h	11
js	12
k	13
m	14
n	15
p	16
r	17
s	18
t	19
u	20
v	21
x	22
y	23
za	24
zb	25
zc	26
z	27
\.


--
-- Data for Name: deviation_code; Type: TABLE DATA; Schema: esdp; Owner: olegmm
--

COPY esdp.deviation_code (dev_code) FROM stdin;
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
\.


--
-- Data for Name: element_type; Type: TABLE DATA; Schema: esdp; Owner: olegmm
--

COPY esdp.element_type (el_type) FROM stdin;
hole
shaft
\.


--
-- Data for Name: main_reference; Type: TABLE DATA; Schema: esdp; Owner: olegmm
--

COPY esdp.main_reference (rtt_id, dev_code, es, ei) FROM stdin;
\.


--
-- Data for Name: range_tolerance_by_type; Type: TABLE DATA; Schema: esdp; Owner: olegmm
--

COPY esdp.range_tolerance_by_type (rtt_id, el_type, bas_tol_id, range_id) FROM stdin;
\.


--
-- Data for Name: size_range; Type: TABLE DATA; Schema: esdp; Owner: olegmm
--

COPY esdp.size_range (range_id, s_range) FROM stdin;
\.


--
-- Data for Name: def_deviances; Type: TABLE DATA; Schema: ost22; Owner: olegmm
--

COPY ost22.def_deviances (nom_dim_range, hole, shaft, quasi_hole, quasi_shaft) FROM stdin;
[0.1,0.3]	0.600	0.600	0.100	0.600
(0.3,1]	0.100	0.100	0.140	0.140
(1,3]	0.140	0.140	0.250	0.250
(3,6]	0.180	0.180	0.300	0.300
(6,10]	0.220	0.220	0.360	0.360
(10,18]	0.270	0.270	0.430	0.430
(18,30]	0.330	0.330	0.520	0.520
(30,50]	0.390	0.390	0.620	0.620
(50,80]	0.460	0.460	0.740	0.740
(80,120]	0.540	0.540	0.870	0.870
(120,180]	0.630	0.630	1.000	1.000
(180,250]	0.720	0.720	1.150	1.150
(250,315]	0.810	0.810	1.300	1.300
(315,400]	0.890	0.890	1.400	1.400
(400,500]	0.970	0.970	1.550	1.550
(500,630]	1.100	1.100	1.750	1.750
(630,800]	1.250	1.250	2.000	2.000
(800,1000]	1.250	1.250	2.300	2.300
(1000,1250]	1.250	1.250	2.600	2.600
(1250,1600]	1.250	1.250	3.100	3.100
(1600,2000]	1.500	1.500	3.700	3.700
(2000,2500]	1.750	1.750	4.400	4.400
(2500,3150]	2.100	2.100	5.400	5.400
(3150,4000]	2.600	2.600	6.600	6.600
(4000,5000]	3.200	3.200	8.000	8.000
(5000,6300]	4.000	4.000	9.800	9.800
(6300,8000]	4.900	4.900	12.000	12.000
(8000,10000]	6.000	6.000	15.000	15.000
\.


--
-- Data for Name: undef_deviances; Type: TABLE DATA; Schema: ost22; Owner: olegmm
--

COPY ost22.undef_deviances (dim_range, deviance) FROM stdin;
[0.1,0.3]	0.050
(0.3,0.5]	0.070
(0.5,3.0]	0.150
(3,30]	0.200
(30,120]	0.300
(120,315]	0.500
(315,1000]	0.800
(1000,2000]	1.200
(2000,3150]	2.000
(3150,5000]	3.000
(5000,8000]	5.000
(8000,10000]	8.000
\.


--
-- Data for Name: basic_deviances; Type: TABLE DATA; Schema: thread_m; Owner: olegmm
--

COPY thread_m.basic_deviances (dev_letter, deviances_arr) FROM stdin;
d	{NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0.090,0.095,0.095,0.100,0.100,0.106,0.112,0.118,0.125,0.132,0.132,0.140,0.150,NULL}
e	{NULL,NULL,NULL,NULL,NULL,NULL,0.050,0.053,0.056,0.056,0.060,0.060,0.063,0.067,0.071,0.071,0.080,0.085,0.090,0.095,0.100,0.106,0.112,0.118,0.140}
f	{0.032,0.033,0.033,0.034,0.034,0.035,0.036,0.036,0.038,0.038,0.038,0.040,0.042,0.045,0.048,0.052,0.058,0.063,0.070,0.075,0.080,0.085,0.090,0.095,0.118}
g	{0.017,0.018,0.018,0.019,0.019,0.020,0.020,0.021,0.022,0.022,0.024,0.026,0.028,0.032,0.034,0.038,0.042,0.048,0.053,0.060,0.063,0.071,0.075,0.080,0.100}
h	{0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000,0.000}
\.


--
-- Data for Name: nom_diam_tolerances; Type: TABLE DATA; Schema: thread_m; Owner: olegmm
--

COPY thread_m.nom_diam_tolerances (tolerance_number, nom_diam_tolerance) FROM stdin;
4	{0.036,0.042,0.048,0.053,0.060,0.063,0.067,0.080,0.090,0.090,0.095,0.112,0.132,0.150,0.170,0.180,0.212,0.236,0.265,0.300,0.315,0.335,0.355,0.375,0.450}
6	{0.056,0.067,0.075,0.085,0.095,0.100,0.106,0.125,0.140,0.140,0.150,0.180,0.212,0.236,0.265,0.280,0.335,0.375,0.425,0.475,0.500,0.530,0.560,0.600,0.710}
8	{NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0.236,0.280,0.335,0.375,0.425,0.450,0.530,0.600,0.670,0.750,0.800,0.850,0.900,0.950,1.180}
\.


--
-- Data for Name: nom_diams; Type: TABLE DATA; Schema: thread_m; Owner: olegmm
--

COPY thread_m.nom_diams (nom_diam, pitch_default) FROM stdin;
0.250	0.075
0.300	0.080
0.350	0.090
0.400	0.100
0.450	0.100
0.500	0.125
0.550	0.125
0.600	0.150
0.700	0.175
0.800	0.200
0.900	0.225
1.000	0.250
1.100	0.250
1.200	0.250
1.400	0.300
1.600	0.350
1.800	0.350
2.000	0.400
2.200	0.450
2.500	0.450
3.000	0.500
3.500	0.600
4.000	0.700
4.500	0.750
5.000	0.800
5.500	\N
6.000	1.000
7.000	1.000
8.000	1.250
9.000	1.250
10.000	1.500
11.000	1.500
12.000	1.750
14.000	2.000
15.000	\N
16.000	2.000
17.000	\N
18.000	2.500
20.000	2.500
22.000	2.500
24.000	3.000
25.000	\N
26.000	\N
27.000	3.000
28.000	\N
30.000	3.500
32.000	\N
33.000	3.500
35.000	\N
36.000	4.000
38.000	\N
39.000	4.000
40.000	\N
42.000	4.500
45.000	4.500
48.000	5.000
50.000	\N
52.000	5.000
55.000	\N
56.000	5.500
58.000	\N
60.000	5.500
62.000	\N
64.000	6.000
65.000	\N
68.000	6.000
70.000	\N
72.000	\N
75.000	\N
76.000	\N
78.000	\N
80.000	\N
82.000	\N
85.000	\N
90.000	\N
95.000	\N
100.000	\N
105.000	\N
110.000	\N
115.000	\N
120.000	\N
125.000	\N
130.000	\N
135.000	\N
140.000	\N
145.000	\N
150.000	\N
155.000	\N
160.000	\N
165.000	\N
170.000	\N
175.000	\N
180.000	\N
185.000	\N
190.000	\N
195.000	\N
200.000	\N
205.000	\N
210.000	\N
215.000	\N
220.000	\N
225.000	\N
230.000	\N
235.000	\N
240.000	\N
245.000	\N
250.000	\N
255.000	\N
260.000	\N
265.000	\N
270.000	\N
275.000	\N
280.000	\N
285.000	\N
290.000	\N
295.000	\N
300.000	\N
310.000	\N
320.000	\N
330.000	\N
340.000	\N
350.000	\N
360.000	\N
370.000	\N
380.000	\N
390.000	\N
400.000	\N
410.000	\N
420.000	\N
430.000	\N
440.000	\N
450.000	\N
460.000	\N
470.000	\N
480.000	\N
490.000	\N
500.000	\N
510.000	\N
520.000	\N
530.000	\N
540.000	\N
550.000	\N
560.000	\N
570.000	\N
580.000	\N
590.000	\N
600.000	\N
\.


--
-- Data for Name: pitch_diam_tolerances; Type: TABLE DATA; Schema: thread_m; Owner: olegmm
--

COPY thread_m.pitch_diam_tolerances (nom_diam_range, pitch, "3", "4", "5", "6", "7", "8", "9", "10") FROM stdin;
[1,1.4]	0.200	24	30	38	48	60	75	\N	\N
[1,1.4]	0.250	26	34	42	53	67	85	\N	\N
[1,1.4]	0.300	28	36	45	56	71	90	\N	\N
[1.4,2.8)	0.200	25	32	40	50	63	80	\N	\N
[1.4,2.8)	0.250	28	36	45	56	71	90	\N	\N
[1.4,2.8)	0.350	32	40	50	63	80	100	\N	\N
[1.4,2.8)	0.400	34	42	53	67	85	106	\N	\N
[1.4,2.8)	0.450	36	45	56	71	90	112	\N	\N
[2.8,5.6)	0.250	28	36	45	56	71	\N	\N	\N
[2.8,5.6)	0.350	34	42	53	67	85	106	\N	\N
[2.8,5.6)	0.500	38	48	60	75	95	118	\N	\N
[2.8,5.6)	0.600	42	53	67	85	106	132	\N	\N
[2.8,5.6)	0.700	45	56	71	90	112	140	\N	\N
[2.8,5.6)	0.750	45	56	71	90	112	140	\N	\N
[2.8,5.6)	0.800	48	60	75	95	118	150	190	236
(5.6,11.2]	0.250	32	40	50	63	80	\N	\N	\N
(5.6,11.2]	0.350	36	45	56	71	90	\N	\N	\N
(5.6,11.2]	0.500	42	53	67	85	106	132	\N	\N
(5.6,11.2]	0.750	50	63	80	100	125	160	\N	\N
(5.6,11.2]	1.000	56	71	90	112	140	180	224	280
(5.6,11.2]	1.250	60	75	95	118	150	190	236	300
(5.6,11.2]	1.500	67	85	106	132	170	212	265	335
(11.2,22.4]	0.350	38	48	60	75	95	\N	\N	\N
(11.2,22.4]	0.500	45	56	71	90	112	140	\N	\N
(11.2,22.4]	0.750	53	67	85	106	132	170	\N	\N
(11.2,22.4]	1.000	60	75	95	118	150	190	236	300
(11.2,22.4]	1.250	67	85	106	132	170	212	265	335
(11.2,22.4]	1.500	71	90	112	140	180	224	280	355
(11.2,22.4]	1.750	75	95	118	150	190	236	300	375
(11.2,22.4]	2.000	80	100	125	160	200	250	315	400
(11.2,22.4]	2.500	85	106	132	170	212	265	335	425
(22.4,45]	0.500	48	60	75	95	118	\N	\N	\N
(22.4,45]	0.750	56	71	90	112	140	180	\N	\N
(22.4,45]	1.000	63	80	100	125	160	200	250	315
(22.4,45]	1.500	75	95	118	150	190	236	300	375
(22.4,45]	2.000	85	106	132	170	212	265	335	425
(22.4,45]	3.000	100	125	160	200	250	315	400	500
(22.4,45]	3.500	106	132	170	212	265	335	425	530
(22.4,45]	4.000	112	140	180	224	280	355	450	560
(22.4,45]	4.500	118	150	190	236	300	375	475	600
(45,90]	0.500	50	63	80	100	125	\N	\N	\N
(45,90]	0.750	60	75	95	118	150	\N	\N	\N
(45,90]	1.000	71	90	112	140	180	224	280	355
(45,90]	1.500	80	100	125	160	200	250	315	400
(45,90]	2.000	90	112	140	180	224	280	355	450
(45,90]	3.000	106	132	170	212	265	335	425	530
(45,90]	4.000	118	150	190	236	300	375	475	600
(45,90]	5.000	125	160	200	250	315	400	500	630
(45,90]	5.500	132	170	212	265	335	425	530	670
(45,90]	6.000	140	180	224	280	355	450	560	710
(90,180]	0.750	63	80	100	125	160	\N	\N	\N
(90,180]	1.000	75	95	118	150	190	\N	\N	\N
(90,180]	1.500	85	106	132	170	212	265	335	425
(90,180]	2.000	95	118	150	190	236	300	375	475
(90,180]	3.000	112	140	180	224	280	355	450	560
(90,180]	4.000	125	160	200	250	315	400	500	630
(90,180]	6.000	150	190	236	300	375	475	600	750
(90,180]	8.000	170	212	265	335	425	530	670	850
(180,355]	1.500	90	112	140	180	224	280	355	\N
(180,355]	2.000	106	132	170	212	265	335	425	530
(180,355]	3.000	125	160	200	250	315	400	500	630
(180,355]	4.000	140	180	224	280	355	450	560	710
(180,355]	6.000	160	200	250	315	400	500	630	800
(180,355]	8.000	180	224	280	355	450	560	710	900
(355,600]	2.000	112	140	180	224	280	355	450	\N
(355,600]	4.000	150	190	236	300	375	475	600	750
(355,600]	6.000	170	212	265	335	425	530	670	850
(355,600]	8.000	190	236	300	375	475	600	750	950
\.


--
-- Data for Name: pitches; Type: TABLE DATA; Schema: thread_m; Owner: olegmm
--

COPY thread_m.pitches (pitch_id, pitch) FROM stdin;
1	0.200
2	0.250
3	0.300
4	0.350
5	0.400
6	0.450
7	0.500
8	0.600
9	0.700
10	0.750
11	0.800
12	1.000
13	1.250
14	1.500
15	1.750
16	2.000
17	2.500
18	3.000
19	3.500
20	4.000
21	4.500
22	5.000
23	5.500
24	6.000
25	8.000
\.


--
-- Name: basic_tolerance_bas_tol_id_seq; Type: SEQUENCE SET; Schema: esdp; Owner: olegmm
--

SELECT pg_catalog.setval('esdp.basic_tolerance_bas_tol_id_seq', 27, true);


--
-- Name: range_range_id_seq; Type: SEQUENCE SET; Schema: esdp; Owner: olegmm
--

SELECT pg_catalog.setval('esdp.range_range_id_seq', 19, true);


--
-- Name: basic_tolerance basic_tolerance_pk; Type: CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.basic_tolerance
    ADD CONSTRAINT basic_tolerance_pk PRIMARY KEY (bas_tol_id);


--
-- Name: deviation_code deviation_code_pk; Type: CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.deviation_code
    ADD CONSTRAINT deviation_code_pk PRIMARY KEY (dev_code);


--
-- Name: element_type element_type_pkey; Type: CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.element_type
    ADD CONSTRAINT element_type_pkey PRIMARY KEY (el_type);


--
-- Name: main_reference main_reference_pk; Type: CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.main_reference
    ADD CONSTRAINT main_reference_pk PRIMARY KEY (rtt_id, dev_code);


--
-- Name: size_range range_pk; Type: CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.size_range
    ADD CONSTRAINT range_pk PRIMARY KEY (range_id);


--
-- Name: range_tolerance_by_type range_tolerance_by_type_pk; Type: CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.range_tolerance_by_type
    ADD CONSTRAINT range_tolerance_by_type_pk PRIMARY KEY (rtt_id);


--
-- Name: size_range range_unique; Type: CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.size_range
    ADD CONSTRAINT range_unique UNIQUE (s_range);


--
-- Name: def_deviances def_deviances_pkey; Type: CONSTRAINT; Schema: ost22; Owner: olegmm
--

ALTER TABLE ONLY ost22.def_deviances
    ADD CONSTRAINT def_deviances_pkey PRIMARY KEY (nom_dim_range);


--
-- Name: undef_deviances unspec_deviances_pkey; Type: CONSTRAINT; Schema: ost22; Owner: olegmm
--

ALTER TABLE ONLY ost22.undef_deviances
    ADD CONSTRAINT unspec_deviances_pkey PRIMARY KEY (dim_range);


--
-- Name: basic_deviances basic_deviances_pkey; Type: CONSTRAINT; Schema: thread_m; Owner: olegmm
--

ALTER TABLE ONLY thread_m.basic_deviances
    ADD CONSTRAINT basic_deviances_pkey PRIMARY KEY (dev_letter);


--
-- Name: nom_diam_tolerances nom_diam_tolerances_pkey; Type: CONSTRAINT; Schema: thread_m; Owner: olegmm
--

ALTER TABLE ONLY thread_m.nom_diam_tolerances
    ADD CONSTRAINT nom_diam_tolerances_pkey PRIMARY KEY (tolerance_number);


--
-- Name: nom_diams nom_diams_pkey; Type: CONSTRAINT; Schema: thread_m; Owner: olegmm
--

ALTER TABLE ONLY thread_m.nom_diams
    ADD CONSTRAINT nom_diams_pkey PRIMARY KEY (nom_diam);


--
-- Name: pitch_diam_tolerances pitch_diam_tolerances_pkey; Type: CONSTRAINT; Schema: thread_m; Owner: olegmm
--

ALTER TABLE ONLY thread_m.pitch_diam_tolerances
    ADD CONSTRAINT pitch_diam_tolerances_pkey PRIMARY KEY (nom_diam_range, pitch);


--
-- Name: pitches pk_pitches; Type: CONSTRAINT; Schema: thread_m; Owner: olegmm
--

ALTER TABLE ONLY thread_m.pitches
    ADD CONSTRAINT pk_pitches PRIMARY KEY (pitch_id);


--
-- Name: idx_nom_dim_range; Type: INDEX; Schema: ost22; Owner: olegmm
--

CREATE INDEX idx_nom_dim_range ON ost22.def_deviances USING gist (nom_dim_range);


--
-- Name: main_reference main_reference_dev_code_fkey; Type: FK CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.main_reference
    ADD CONSTRAINT main_reference_dev_code_fkey FOREIGN KEY (dev_code) REFERENCES esdp.deviation_code(dev_code);


--
-- Name: main_reference main_reference_rtt_id_fkey; Type: FK CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.main_reference
    ADD CONSTRAINT main_reference_rtt_id_fkey FOREIGN KEY (rtt_id) REFERENCES esdp.range_tolerance_by_type(rtt_id);


--
-- Name: range_tolerance_by_type range_tolerance_by_type_bas_tol_id_fkey; Type: FK CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.range_tolerance_by_type
    ADD CONSTRAINT range_tolerance_by_type_bas_tol_id_fkey FOREIGN KEY (bas_tol_id) REFERENCES esdp.basic_tolerance(bas_tol_id);


--
-- Name: range_tolerance_by_type range_tolerance_by_type_el_type_fkey; Type: FK CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.range_tolerance_by_type
    ADD CONSTRAINT range_tolerance_by_type_el_type_fkey FOREIGN KEY (el_type) REFERENCES esdp.element_type(el_type);


--
-- Name: range_tolerance_by_type range_tolerance_by_type_range_fk; Type: FK CONSTRAINT; Schema: esdp; Owner: olegmm
--

ALTER TABLE ONLY esdp.range_tolerance_by_type
    ADD CONSTRAINT range_tolerance_by_type_range_fk FOREIGN KEY (range_id) REFERENCES esdp.size_range(range_id);


--
-- Name: SCHEMA esdp; Type: ACL; Schema: -; Owner: olegmm
--

GRANT ALL ON SCHEMA esdp TO otkreader;


--
-- Name: SCHEMA ost22; Type: ACL; Schema: -; Owner: olegmm
--

GRANT USAGE ON SCHEMA ost22 TO otkreader;


--
-- Name: SCHEMA thread_m; Type: ACL; Schema: -; Owner: olegmm
--

GRANT USAGE ON SCHEMA thread_m TO otkreader;


--
-- Name: TABLE def_deviances; Type: ACL; Schema: ost22; Owner: olegmm
--

GRANT SELECT ON TABLE ost22.def_deviances TO otkreader;


--
-- Name: TABLE undef_deviances; Type: ACL; Schema: ost22; Owner: olegmm
--

GRANT SELECT ON TABLE ost22.undef_deviances TO otkreader;


--
-- Name: TABLE basic_deviances; Type: ACL; Schema: thread_m; Owner: olegmm
--

GRANT SELECT ON TABLE thread_m.basic_deviances TO otkreader;


--
-- Name: TABLE nom_diam_tolerances; Type: ACL; Schema: thread_m; Owner: olegmm
--

GRANT SELECT ON TABLE thread_m.nom_diam_tolerances TO otkreader;


--
-- Name: TABLE nom_diams; Type: ACL; Schema: thread_m; Owner: olegmm
--

GRANT SELECT ON TABLE thread_m.nom_diams TO otkreader;


--
-- Name: TABLE pitch_diam_tolerances; Type: ACL; Schema: thread_m; Owner: olegmm
--

GRANT SELECT ON TABLE thread_m.pitch_diam_tolerances TO otkreader;


--
-- Name: TABLE pitches; Type: ACL; Schema: thread_m; Owner: olegmm
--

GRANT SELECT ON TABLE thread_m.pitches TO otkreader;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: ost22; Owner: olegmm
--

ALTER DEFAULT PRIVILEGES FOR ROLE otkreader IN SCHEMA ost22 GRANT SELECT ON TABLES  TO otkreader;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: thread_m; Owner: olegmm
--

ALTER DEFAULT PRIVILEGES FOR ROLE otkreader IN SCHEMA thread_m GRANT SELECT ON TABLES  TO otkreader;


--
-- PostgreSQL database dump complete
--

