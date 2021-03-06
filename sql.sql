PGDMP     +    	                y            balance    12.4    12.4                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    24767    balance    DATABASE     ?   CREATE DATABASE balance WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Russian_Russia.1251' LC_CTYPE = 'Russian_Russia.1251';
    DROP DATABASE balance;
                postgres    false            ?            1259    24776    users    TABLE     ?   CREATE TABLE public.users (
    id bigint NOT NULL,
    username character varying(32),
    password character varying(100),
    next_date timestamp without time zone,
    attempt_count integer,
    token character varying(16),
    state integer
);
    DROP TABLE public.users;
       public         heap    postgres    false            ?            1259    24779    User_id_seq    SEQUENCE     v   CREATE SEQUENCE public."User_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public."User_id_seq";
       public          postgres    false    202                       0    0    User_id_seq    SEQUENCE OWNED BY     >   ALTER SEQUENCE public."User_id_seq" OWNED BY public.users.id;
          public          postgres    false    203            ?            1259    24781    balance    TABLE     y   CREATE TABLE public.balance (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    balance numeric(16,2) NOT NULL
);
    DROP TABLE public.balance;
       public         heap    postgres    false            ?            1259    24784    balance_id_seq    SEQUENCE     w   CREATE SEQUENCE public.balance_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.balance_id_seq;
       public          postgres    false    204                       0    0    balance_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.balance_id_seq OWNED BY public.balance.id;
          public          postgres    false    205            ?            1259    24796    customer_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;
 &   DROP SEQUENCE public.customer_id_seq;
       public          postgres    false            ?
           2604    24838 
   balance id    DEFAULT     h   ALTER TABLE ONLY public.balance ALTER COLUMN id SET DEFAULT nextval('public.balance_id_seq'::regclass);
 9   ALTER TABLE public.balance ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    205    204            ?
           2604    24845    users id    DEFAULT     e   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public."User_id_seq"'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    203    202                      0    24781    balance 
   TABLE DATA           7   COPY public.balance (id, user_id, balance) FROM stdin;
    public          postgres    false    204   ?                 0    24776    users 
   TABLE DATA           _   COPY public.users (id, username, password, next_date, attempt_count, token, state) FROM stdin;
    public          postgres    false    202                     0    0    User_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public."User_id_seq"', 10, true);
          public          postgres    false    203                       0    0    balance_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.balance_id_seq', 3, true);
          public          postgres    false    205                       0    0    customer_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.customer_id_seq', 3, true);
          public          postgres    false    206            ?
           2606    24849    users User_pkey 
   CONSTRAINT     O   ALTER TABLE ONLY public.users
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (id);
 ;   ALTER TABLE ONLY public.users DROP CONSTRAINT "User_pkey";
       public            postgres    false    202            ?
           2606    24851    balance balance_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.balance
    ADD CONSTRAINT balance_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.balance DROP CONSTRAINT balance_pkey;
       public            postgres    false    204                  x?3?44?4?31?????? K"         P   x?34?L?K)J??L54HLI6?4?LJ4?LLJJ55K505O32H??0N????4?t?1?07??4	?4?մ?4?????? ?*?     