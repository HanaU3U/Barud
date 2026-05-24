--
-- PostgreSQL database dump
--

CREATE DATABASE "BARUD" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Spain.1252';


ALTER DATABASE "BARUD" OWNER TO postgres;


CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;


COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;


CREATE TABLE public.cuenta (
    id_cuenta integer NOT NULL,
    id_pedido integer NOT NULL,
    subtotal numeric(10,2) DEFAULT 0 NOT NULL,
    impuestos numeric(10,2) DEFAULT 0 NOT NULL,
    total numeric(10,2) DEFAULT 0 NOT NULL,
    estado character varying(20) DEFAULT 'Abierta'::character varying NOT NULL,
    CONSTRAINT cuenta_estado_check CHECK (((estado)::text = ANY ((ARRAY['Abierta'::character varying, 'Cerrada'::character varying])::text[]))),
    CONSTRAINT cuenta_impuestos_check CHECK ((impuestos >= (0)::numeric)),
    CONSTRAINT cuenta_subtotal_check CHECK ((subtotal >= (0)::numeric)),
    CONSTRAINT cuenta_total_check CHECK ((total >= (0)::numeric))
);


ALTER TABLE public.cuenta OWNER TO postgres;



CREATE SEQUENCE public.cuenta_id_cuenta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cuenta_id_cuenta_seq OWNER TO postgres;

ALTER SEQUENCE public.cuenta_id_cuenta_seq OWNED BY public.cuenta.id_cuenta;


CREATE TABLE public.detalle_pedido (
    id_detalle integer NOT NULL,
    id_pedido integer NOT NULL,
    id_producto integer NOT NULL,
    cantidad integer NOT NULL,
    precio_unitario numeric(10,2) NOT NULL,
    estado character varying(20) DEFAULT 'Activo'::character varying NOT NULL,
    CONSTRAINT detalle_pedido_cantidad_check CHECK ((cantidad > 0)),
    CONSTRAINT detalle_pedido_estado_check CHECK (((estado)::text = ANY ((ARRAY['Activo'::character varying, 'Cancelado'::character varying])::text[]))),
    CONSTRAINT detalle_pedido_precio_unitario_check CHECK ((precio_unitario >= (0)::numeric))
);


ALTER TABLE public.detalle_pedido OWNER TO postgres;


CREATE SEQUENCE public.detalle_pedido_id_detalle_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.detalle_pedido_id_detalle_seq OWNER TO postgres;


ALTER SEQUENCE public.detalle_pedido_id_detalle_seq OWNED BY public.detalle_pedido.id_detalle;

CREATE TABLE public.division_cuenta (
    id_division integer NOT NULL,
    id_cuenta integer NOT NULL,
    descripcion character varying(100),
    monto numeric(10,2) DEFAULT 0 NOT NULL,
    CONSTRAINT descripcion_no_vacia CHECK (((descripcion IS NULL) OR (TRIM(BOTH FROM descripcion) <> ''::text))),
    CONSTRAINT division_cuenta_monto_check CHECK ((monto >= (0)::numeric))
);


ALTER TABLE public.division_cuenta OWNER TO postgres;

CREATE SEQUENCE public.division_cuenta_id_division_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.division_cuenta_id_division_seq OWNER TO postgres;

ALTER SEQUENCE public.division_cuenta_id_division_seq OWNED BY public.division_cuenta.id_division;


CREATE TABLE public.empleado (
    id_empleado integer NOT NULL,
    nombre character varying(100) NOT NULL,
    rol character varying(50) NOT NULL,
    fecha_ingreso date DEFAULT CURRENT_DATE NOT NULL,
    estado character varying(20) DEFAULT 'Activo'::character varying NOT NULL,
    CONSTRAINT empleado_estado_check CHECK (((estado)::text = ANY ((ARRAY['Activo'::character varying, 'Inactivo'::character varying])::text[]))),
    CONSTRAINT empleado_rol_check CHECK (((rol)::text = ANY ((ARRAY['Mesero'::character varying, 'Bartender'::character varying, 'Cajero'::character varying, 'Administrador'::character varying])::text[]))),
    CONSTRAINT nombre_no_vacio CHECK ((TRIM(BOTH FROM nombre) <> ''::text))
);


ALTER TABLE public.empleado OWNER TO postgres;

CREATE SEQUENCE public.empleado_id_empleado_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.empleado_id_empleado_seq OWNER TO postgres;

ALTER SEQUENCE public.empleado_id_empleado_seq OWNED BY public.empleado.id_empleado;

CREATE TABLE public.mesa (
    id_mesa integer NOT NULL,
    numero integer NOT NULL,
    capacidad integer DEFAULT 1 NOT NULL,
    estado character varying(20) DEFAULT 'Disponible'::character varying NOT NULL,
    CONSTRAINT mesa_capacidad_check CHECK ((capacidad > 0)),
    CONSTRAINT mesa_estado_check CHECK (((estado)::text = ANY ((ARRAY['Disponible'::character varying, 'Ocupada'::character varying, 'Reservada'::character varying])::text[])))
);


ALTER TABLE public.mesa OWNER TO postgres;


CREATE SEQUENCE public.mesa_id_mesa_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.mesa_id_mesa_seq OWNER TO postgres;

ALTER SEQUENCE public.mesa_id_mesa_seq OWNED BY public.mesa.id_mesa;


CREATE TABLE public.pago (
    id_pago integer NOT NULL,
    id_cuenta integer NOT NULL,
    metodo character varying(30) DEFAULT 'Efectivo'::character varying NOT NULL,
    monto numeric(10,2) NOT NULL,
    fecha timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT monto_positivo CHECK ((monto > (0)::numeric)),
    CONSTRAINT pago_metodo_check CHECK (((metodo)::text = ANY ((ARRAY['Efectivo'::character varying, 'Tarjeta'::character varying, 'Transferencia'::character varying])::text[]))),
    CONSTRAINT pago_monto_check CHECK ((monto > (0)::numeric))
);


ALTER TABLE public.pago OWNER TO postgres;

CREATE SEQUENCE public.pago_id_pago_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pago_id_pago_seq OWNER TO postgres;


ALTER SEQUENCE public.pago_id_pago_seq OWNED BY public.pago.id_pago;


CREATE TABLE public.pedido (
    id_pedido integer NOT NULL,
    id_mesa integer NOT NULL,
    id_mesero integer NOT NULL,
    fecha_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    numero_personas integer NOT NULL,
    estado character varying(30) DEFAULT 'Abierto'::character varying NOT NULL,
    CONSTRAINT pedido_estado_check CHECK (((estado)::text = ANY ((ARRAY['Abierto'::character varying, 'En preparacion'::character varying, 'Cerrado'::character varying])::text[]))),
    CONSTRAINT pedido_numero_personas_check CHECK ((numero_personas > 0))
);


ALTER TABLE public.pedido OWNER TO postgres;


CREATE SEQUENCE public.pedido_id_pedido_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pedido_id_pedido_seq OWNER TO postgres;


ALTER SEQUENCE public.pedido_id_pedido_seq OWNED BY public.pedido.id_pedido;


CREATE TABLE public.producto (
    id_producto integer NOT NULL,
    nombre character varying(100) NOT NULL,
    tipo character varying(50) NOT NULL,
    precio numeric(10,2) DEFAULT 0 NOT NULL,
    stock integer DEFAULT 0 NOT NULL,
    CONSTRAINT nombre_producto_no_vacio CHECK ((TRIM(BOTH FROM nombre) <> ''::text)),
    CONSTRAINT producto_precio_check CHECK ((precio >= (0)::numeric)),
    CONSTRAINT producto_stock_check CHECK ((stock >= 0)),
    CONSTRAINT producto_tipo_check CHECK (((tipo)::text = ANY ((ARRAY['Bebida alcoholica'::character varying, 'Bebida no alcoholica'::character varying, 'Comida'::character varying])::text[])))
);


ALTER TABLE public.producto OWNER TO postgres;

CREATE SEQUENCE public.producto_id_producto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.producto_id_producto_seq OWNER TO postgres;


ALTER SEQUENCE public.producto_id_producto_seq OWNED BY public.producto.id_producto;


-- VISTA: v_cuenta_mesa
-- Combina las tablas cuenta, pedido y mesa.
-- Muestra el resumen de cada cuenta junto con el número de mesa asociado.
-- Usada en el endpoint GET /api/vistas/cuenta-mesa.

CREATE VIEW public.v_cuenta_mesa AS
 SELECT c.id_cuenta,
    m.numero AS numero_mesa,
    c.subtotal,
    c.impuestos,
    c.total,
    c.estado
   FROM ((public.cuenta c
     JOIN public.pedido p ON ((c.id_pedido = p.id_pedido)))
     JOIN public.mesa m ON ((p.id_mesa = m.id_mesa)));


ALTER VIEW public.v_cuenta_mesa OWNER TO postgres;


-- VISTA: v_detalle_cuenta_mesa
-- Combina las tablas cuenta, pedido, mesa, detalle_pedido, producto y division_cuenta.
-- Muestra el detalle completo de las cuentas, incluyendo productos consumidos,
-- subtotal, impuestos, total y si la cuenta fue dividida.
-- Usada en el endpoint GET /api/vistas/detalle-cuenta-mesa.

CREATE VIEW public.v_detalle_cuenta_mesa AS
 SELECT c.id_cuenta,
    m.numero AS numero_mesa,
    string_agg((((prod.nombre)::text || ' x'::text) || dp.cantidad), ', '::text) AS resumen_productos,
    c.subtotal,
    c.impuestos,
    c.total,
    p.fecha_hora AS fecha,
        CASE
            WHEN (count(dc.id_division) > 0) THEN 'Si'::text
            ELSE 'No'::text
        END AS cuenta_dividida
   FROM (((((public.cuenta c
     JOIN public.pedido p ON ((c.id_pedido = p.id_pedido)))
     JOIN public.mesa m ON ((p.id_mesa = m.id_mesa)))
     JOIN public.detalle_pedido dp ON ((p.id_pedido = dp.id_pedido)))
     JOIN public.producto prod ON ((dp.id_producto = prod.id_producto)))
     LEFT JOIN public.division_cuenta dc ON ((c.id_cuenta = dc.id_cuenta)))
  WHERE ((dp.estado)::text = 'Activo'::text)
  GROUP BY c.id_cuenta, m.numero, c.subtotal, c.impuestos, c.total, p.fecha_hora;


ALTER VIEW public.v_detalle_cuenta_mesa OWNER TO postgres;

-- VISTA: v_detalle_pedido_completo
-- Combina las tablas detalle_pedido, pedido, mesa y producto.
-- Muestra el detalle completo de cada pedido con producto, cantidad,
-- precio unitario, subtotal y estado.
-- Usada en el endpoint GET /api/vistas/detalle-pedido-completo.

CREATE VIEW public.v_detalle_pedido_completo AS
 SELECT p.id_pedido,
    m.numero AS mesa,
    prod.nombre AS producto,
    dp.cantidad,
    dp.precio_unitario,
    ((dp.cantidad)::numeric * dp.precio_unitario) AS subtotal,
    dp.estado
   FROM (((public.detalle_pedido dp
     JOIN public.pedido p ON ((dp.id_pedido = p.id_pedido)))
     JOIN public.mesa m ON ((p.id_mesa = m.id_mesa)))
     JOIN public.producto prod ON ((dp.id_producto = prod.id_producto)));


ALTER VIEW public.v_detalle_pedido_completo OWNER TO postgres;

-- VISTA: v_detalle_pedido_mesero
-- Combina las tablas detalle_pedido, pedido y producto.
-- Muestra los productos activos asociados a los pedidos junto con cantidades
-- y subtotales, orientado a la visualización para meseros.
-- Usada en el endpoint GET /api/vistas/detalle-pedido-mesero.

CREATE VIEW public.v_detalle_pedido_mesero AS
 SELECT p.id_pedido,
    prod.nombre AS producto,
    dp.cantidad,
    dp.precio_unitario,
    ((dp.cantidad)::numeric * dp.precio_unitario) AS subtotal
   FROM ((public.detalle_pedido dp
     JOIN public.pedido p ON ((dp.id_pedido = p.id_pedido)))
     JOIN public.producto prod ON ((dp.id_producto = prod.id_producto)))
  WHERE ((dp.estado)::text = 'Activo'::text);


ALTER VIEW public.v_detalle_pedido_mesero OWNER TO postgres;

-- VISTA: v_division_cuenta_mesa
-- Combina las tablas division_cuenta, cuenta, pedido y mesa.
-- Muestra las divisiones de cuenta realizadas por mesa.
-- Usada en el endpoint GET /api/vistas/division-cuenta-mesa.

CREATE VIEW public.v_division_cuenta_mesa AS
 SELECT dc.id_division,
    m.numero AS numero_mesa,
    dc.descripcion,
    dc.monto
   FROM (((public.division_cuenta dc
     JOIN public.cuenta c ON ((dc.id_cuenta = c.id_cuenta)))
     JOIN public.pedido p ON ((c.id_pedido = p.id_pedido)))
     JOIN public.mesa m ON ((p.id_mesa = m.id_mesa)));


ALTER VIEW public.v_division_cuenta_mesa OWNER TO postgres;


-- VISTA: v_ingresos_dia_semana
-- Consulta la tabla pago.
-- Muestra los ingresos agrupados por día de la semana,
-- incluyendo cantidad de pagos y promedio de ingresos.
-- Usada en el endpoint GET /api/vistas/ingresos-dia-semana.

CREATE VIEW public.v_ingresos_dia_semana AS
 SELECT to_char(fecha, 'Day'::text) AS dia_semana,
    count(id_pago) AS total_pagos,
    sum(monto) AS ingresos_totales,
    avg(monto) AS promedio_pago
   FROM public.pago p
  GROUP BY (to_char(fecha, 'Day'::text))
  ORDER BY (sum(monto)) DESC;


ALTER VIEW public.v_ingresos_dia_semana OWNER TO postgres;


-- VISTA: v_pedidos_activos
-- Combina las tablas pedido, mesa y empleado.
-- Muestra únicamente los pedidos activos o en preparación
-- junto con la información de la mesa y mesero asignado.
-- Usada en el endpoint GET /api/vistas/pedidos-activos.

CREATE VIEW public.v_pedidos_activos AS
 SELECT p.id_pedido,
    m.numero AS numero_mesa,
    e.nombre AS mesero,
    p.fecha_hora,
    p.numero_personas,
    p.estado
   FROM ((public.pedido p
     JOIN public.mesa m ON ((p.id_mesa = m.id_mesa)))
     JOIN public.empleado e ON ((p.id_mesero = e.id_empleado)))
  WHERE ((p.estado)::text = ANY ((ARRAY['Abierto'::character varying, 'En preparacion'::character varying])::text[]));


ALTER VIEW public.v_pedidos_activos OWNER TO postgres;


-- VISTA: v_pedidos_mesero
-- Combina las tablas pedido, mesa y empleado.
-- Muestra todos los pedidos registrados junto con el mesero responsable.
-- Usada en el endpoint GET /api/vistas/pedidos-mesero.

CREATE VIEW public.v_pedidos_mesero AS
 SELECT p.id_pedido,
    m.numero AS numero_mesa,
    e.nombre AS mesero,
    p.fecha_hora,
    p.numero_personas,
    p.estado
   FROM ((public.pedido p
     JOIN public.mesa m ON ((p.id_mesa = m.id_mesa)))
     JOIN public.empleado e ON ((p.id_mesero = e.id_empleado)));


ALTER VIEW public.v_pedidos_mesero OWNER TO postgres;


-- VISTA: v_pedidos_por_dia
-- Combina las tablas pedido y detalle_pedido.
-- Muestra la cantidad total de pedidos e ingresos generados agrupados por fecha.
-- Solo considera detalles de pedido activos.
-- Usada en el endpoint GET /api/vistas/pedidos-por-dia.

CREATE VIEW public.v_pedidos_por_dia AS
 SELECT date(p.fecha_hora) AS fecha,
    count(DISTINCT p.id_pedido) AS total_pedidos,
    sum(((dp.cantidad)::numeric * dp.precio_unitario)) AS ingresos
   FROM (public.pedido p
     JOIN public.detalle_pedido dp ON ((p.id_pedido = dp.id_pedido)))
  WHERE ((dp.estado)::text = 'Activo'::text)
  GROUP BY (date(p.fecha_hora))
  ORDER BY (date(p.fecha_hora)) DESC;


ALTER VIEW public.v_pedidos_por_dia OWNER TO postgres;


-- VISTA: v_productos_disponibles
-- Consulta la tabla producto.
-- Muestra únicamente los productos con stock disponible mayor a cero.
-- Incluye restricción CHECK OPTION para evitar inconsistencias en actualizaciones.
-- Usada en el endpoint GET /api/vistas/productos-disponibles.

CREATE VIEW public.v_productos_disponibles AS
 SELECT id_producto,
    nombre,
    tipo,
    precio,
    stock
   FROM public.producto
  WHERE (stock > 0)
  WITH LOCAL CHECK OPTION;


ALTER VIEW public.v_productos_disponibles OWNER TO postgres;


-- VISTA: v_productos_mas_vendidos
-- Combina las tablas detalle_pedido y producto.
-- Muestra los productos más vendidos junto con la cantidad total vendida
-- e ingresos generados por cada producto.
-- Solo considera detalles de pedido activos.
-- Usada en el endpoint GET /api/vistas/productos-mas-vendidos.

CREATE VIEW public.v_productos_mas_vendidos AS
 SELECT prod.nombre,
    prod.tipo,
    sum(dp.cantidad) AS total_vendido,
    sum(((dp.cantidad)::numeric * dp.precio_unitario)) AS ingresos
   FROM (public.detalle_pedido dp
     JOIN public.producto prod ON ((dp.id_producto = prod.id_producto)))
  WHERE ((dp.estado)::text = 'Activo'::text)
  GROUP BY prod.nombre, prod.tipo
  ORDER BY (sum(dp.cantidad)) DESC;


ALTER VIEW public.v_productos_mas_vendidos OWNER TO postgres;

ALTER TABLE ONLY public.cuenta ALTER COLUMN id_cuenta SET DEFAULT nextval('public.cuenta_id_cuenta_seq'::regclass);


ALTER TABLE ONLY public.detalle_pedido ALTER COLUMN id_detalle SET DEFAULT nextval('public.detalle_pedido_id_detalle_seq'::regclass);


ALTER TABLE ONLY public.division_cuenta ALTER COLUMN id_division SET DEFAULT nextval('public.division_cuenta_id_division_seq'::regclass);

ALTER TABLE ONLY public.empleado ALTER COLUMN id_empleado SET DEFAULT nextval('public.empleado_id_empleado_seq'::regclass);


ALTER TABLE ONLY public.mesa ALTER COLUMN id_mesa SET DEFAULT nextval('public.mesa_id_mesa_seq'::regclass);



ALTER TABLE ONLY public.pago ALTER COLUMN id_pago SET DEFAULT nextval('public.pago_id_pago_seq'::regclass);



ALTER TABLE ONLY public.pedido ALTER COLUMN id_pedido SET DEFAULT nextval('public.pedido_id_pedido_seq'::regclass);


ALTER TABLE ONLY public.producto ALTER COLUMN id_producto SET DEFAULT nextval('public.producto_id_producto_seq'::regclass);

-- DATOS DE PRUEBA

INSERT INTO public.cuenta VALUES (36, 24, 105500.00, 13715.00, 119215.00, 'Cerrada');
INSERT INTO public.cuenta VALUES (37, 25, 47500.00, 6175.00, 53675.00, 'Cerrada');
INSERT INTO public.cuenta VALUES (38, 26, 44000.00, 5720.00, 49720.00, 'Cerrada');
INSERT INTO public.cuenta VALUES (39, 27, 111000.00, 14430.00, 125430.00, 'Cerrada');
INSERT INTO public.cuenta VALUES (40, 28, 103000.00, 13390.00, 116390.00, 'Cerrada');



INSERT INTO public.detalle_pedido VALUES (50, 28, 44, 2, 20000.00, 'Activo');
INSERT INTO public.detalle_pedido VALUES (51, 28, 42, 2, 14000.00, 'Activo');
INSERT INTO public.detalle_pedido VALUES (52, 28, 47, 1, 35000.00, 'Activo');
INSERT INTO public.detalle_pedido VALUES (42, 24, 44, 1, 20000.00, 'Cancelado');
INSERT INTO public.detalle_pedido VALUES (41, 24, 47, 1, 35000.00, 'Cancelado');
INSERT INTO public.detalle_pedido VALUES (43, 24, 42, 3, 14000.00, 'Cancelado');
INSERT INTO public.detalle_pedido VALUES (44, 24, 48, 1, 8500.00, 'Cancelado');
INSERT INTO public.detalle_pedido VALUES (45, 25, 49, 1, 7500.00, 'Cancelado');
INSERT INTO public.detalle_pedido VALUES (46, 25, 44, 2, 20000.00, 'Cancelado');
INSERT INTO public.detalle_pedido VALUES (47, 26, 43, 2, 22000.00, 'Cancelado');
INSERT INTO public.detalle_pedido VALUES (48, 27, 42, 3, 14000.00, 'Cancelado');
INSERT INTO public.detalle_pedido VALUES (49, 27, 45, 3, 23000.00, 'Cancelado');




INSERT INTO public.division_cuenta VALUES (1, 36, 'Marlon', 39738.33);
INSERT INTO public.division_cuenta VALUES (2, 36, 'Pepe', 39738.33);
INSERT INTO public.division_cuenta VALUES (3, 36, 'Liliana', 39738.33);
INSERT INTO public.division_cuenta VALUES (4, 39, 'Maria', 62715.00);
INSERT INTO public.division_cuenta VALUES (5, 39, 'Juan', 62715.00);



INSERT INTO public.empleado VALUES (1, 'Juan Perez', 'Mesero', '2023-01-10', 'Activo');
INSERT INTO public.empleado VALUES (2, 'Ana Gomez', 'Mesero', '2023-02-15', 'Activo');
INSERT INTO public.empleado VALUES (3, 'Carlos Ruiz', 'Mesero', '2023-03-20', 'Activo');
INSERT INTO public.empleado VALUES (4, 'Laura Diaz', 'Bartender', '2023-01-05', 'Activo');
INSERT INTO public.empleado VALUES (5, 'Pedro Lopez', 'Bartender', '2023-02-10', 'Activo');
INSERT INTO public.empleado VALUES (6, 'Maria Torres', 'Cajero', '2023-03-01', 'Activo');
INSERT INTO public.empleado VALUES (7, 'Luis Martinez', 'Administrador', '2022-12-01', 'Activo');
INSERT INTO public.empleado VALUES (8, 'Sofia Ramirez', 'Mesero', '2023-04-01', 'Activo');
INSERT INTO public.empleado VALUES (9, 'Andres Castro', 'Mesero', '2023-05-01', 'Activo');
INSERT INTO public.empleado VALUES (10, 'Valentina Rojas', 'Cajero', '2023-06-01', 'Activo');
INSERT INTO public.empleado VALUES (11, 'Diego Herrera', 'Mesero', '2023-07-01', 'Activo');
INSERT INTO public.empleado VALUES (12, 'Camila Vargas', 'Mesero', '2023-08-01', 'Activo');
INSERT INTO public.empleado VALUES (13, 'Memo Diaz', 'Bartender', '2026-05-17', 'Activo');
INSERT INTO public.empleado VALUES (14, 'Carolina', 'Bartender', '2026-05-24', 'Activo');


INSERT INTO public.mesa VALUES (3, 3, 2, 'Disponible');
INSERT INTO public.mesa VALUES (5, 5, 5, 'Disponible');
INSERT INTO public.mesa VALUES (8, 8, 5, 'Disponible');
INSERT INTO public.mesa VALUES (10, 10, 5, 'Disponible');
INSERT INTO public.mesa VALUES (6, 6, 5, 'Disponible');
INSERT INTO public.mesa VALUES (1, 1, 2, 'Disponible');
INSERT INTO public.mesa VALUES (7, 7, 5, 'Disponible');
INSERT INTO public.mesa VALUES (2, 2, 2, 'Disponible');
INSERT INTO public.mesa VALUES (4, 4, 5, 'Disponible');
INSERT INTO public.mesa VALUES (9, 9, 5, 'Disponible');


INSERT INTO public.pago VALUES (24, 36, 'Efectivo', 119215.00, '2026-05-24 00:17:48');
INSERT INTO public.pago VALUES (25, 37, 'Transferencia', 53675.00, '2026-05-24 00:19:29');
INSERT INTO public.pago VALUES (26, 38, 'Tarjeta', 49720.00, '2026-05-24 00:19:37');
INSERT INTO public.pago VALUES (27, 39, 'Tarjeta', 125430.00, '2026-05-24 00:35:05');
INSERT INTO public.pago VALUES (28, 40, 'Efectivo', 116390.00, '2026-05-24 00:57:11');


INSERT INTO public.pedido VALUES (24, 4, 1, '2026-05-23 23:54:53', 5, 'Cerrado');
INSERT INTO public.pedido VALUES (25, 7, 1, '2026-05-24 00:18:06', 3, 'Cerrado');
INSERT INTO public.pedido VALUES (26, 2, 1, '2026-05-24 00:18:15', 2, 'Cerrado');
INSERT INTO public.pedido VALUES (27, 4, 2, '2026-05-24 00:33:07', 3, 'Cerrado');
INSERT INTO public.pedido VALUES (28, 9, 2, '2026-05-24 00:48:58', 4, 'Cerrado');


INSERT INTO public.producto VALUES (39, 'Cerveza Corona', 'Bebida alcoholica', 8500.00, 20);
INSERT INTO public.producto VALUES (40, 'Cerveza Poker', 'Bebida alcoholica', 7000.00, 13);
INSERT INTO public.producto VALUES (41, 'Cerveza Tres Coordilleras', 'Bebida alcoholica', 12000.00, 12);
INSERT INTO public.producto VALUES (42, 'Cerveza Stella Artois', 'Bebida alcoholica', 14000.00, 22);
INSERT INTO public.producto VALUES (45, 'Hamburgesa', 'Comida', 23000.00, 12);
INSERT INTO public.producto VALUES (46, 'Papas Francesas', 'Comida', 7500.00, 40);
INSERT INTO public.producto VALUES (47, 'Picada', 'Comida', 35000.00, 33);
INSERT INTO public.producto VALUES (48, 'Agua', 'Bebida no alcoholica', 8500.00, 37);
INSERT INTO public.producto VALUES (49, 'Jugo Mora', 'Bebida no alcoholica', 7500.00, 34);
INSERT INTO public.producto VALUES (44, 'Piña Colada', 'Bebida alcoholica', 20000.00, 15);
INSERT INTO public.producto VALUES (43, 'Cosmopolitan', 'Bebida alcoholica', 22000.00, 32);
INSERT INTO public.producto VALUES (50, 'Jugo de Mango', 'Bebida no alcoholica', 12000.00, 4);



SELECT pg_catalog.setval('public.cuenta_id_cuenta_seq', 40, true);



SELECT pg_catalog.setval('public.detalle_pedido_id_detalle_seq', 52, true);

SELECT pg_catalog.setval('public.division_cuenta_id_division_seq', 5, true);



SELECT pg_catalog.setval('public.empleado_id_empleado_seq', 14, true);


SELECT pg_catalog.setval('public.mesa_id_mesa_seq', 10, true);


SELECT pg_catalog.setval('public.pago_id_pago_seq', 28, true);


SELECT pg_catalog.setval('public.pedido_id_pedido_seq', 28, true);


SELECT pg_catalog.setval('public.producto_id_producto_seq', 50, true);


ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_id_pedido_key UNIQUE (id_pedido);



ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (id_cuenta);



ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT detalle_pedido_pkey PRIMARY KEY (id_detalle);


ALTER TABLE ONLY public.division_cuenta
    ADD CONSTRAINT division_cuenta_pkey PRIMARY KEY (id_division);



ALTER TABLE ONLY public.empleado
    ADD CONSTRAINT empleado_pkey PRIMARY KEY (id_empleado);

ALTER TABLE ONLY public.mesa
    ADD CONSTRAINT mesa_numero_key UNIQUE (numero);



ALTER TABLE ONLY public.mesa
    ADD CONSTRAINT mesa_pkey PRIMARY KEY (id_mesa);


ALTER TABLE ONLY public.pago
    ADD CONSTRAINT pago_pkey PRIMARY KEY (id_pago);



ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (id_pedido);



ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_pkey PRIMARY KEY (id_producto);



CREATE INDEX idx_pago_cuenta ON public.pago USING btree (id_cuenta);


CREATE INDEX idx_pedido_mesa_estado ON public.pedido USING btree (id_mesa, estado);


CREATE INDEX idx_producto_nombre ON public.producto USING btree (nombre);



ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT fk_cuenta_pedido FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido) ON UPDATE CASCADE ON DELETE CASCADE;



ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT fk_detalle_pedido FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido) ON DELETE CASCADE;



ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto) REFERENCES public.producto(id_producto) ON UPDATE CASCADE ON DELETE RESTRICT;



ALTER TABLE ONLY public.division_cuenta
    ADD CONSTRAINT fk_division_cuenta FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(id_cuenta) ON DELETE CASCADE;


ALTER TABLE ONLY public.pago
    ADD CONSTRAINT fk_pago_cuenta FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(id_cuenta) ON DELETE CASCADE;


ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fk_pedido_mesa FOREIGN KEY (id_mesa) REFERENCES public.mesa(id_mesa) ON UPDATE CASCADE ON DELETE RESTRICT;



ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fk_pedido_mesero FOREIGN KEY (id_mesero) REFERENCES public.empleado(id_empleado) ON UPDATE CASCADE ON DELETE RESTRICT;


GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.cuenta TO admin_rol;


GRANT SELECT,USAGE ON SEQUENCE public.cuenta_id_cuenta_seq TO admin_rol;
GRANT SELECT,USAGE ON SEQUENCE public.cuenta_id_cuenta_seq TO mesero_rol;


GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.detalle_pedido TO admin_rol;
GRANT INSERT,UPDATE ON TABLE public.detalle_pedido TO mesero_rol;


GRANT SELECT,USAGE ON SEQUENCE public.detalle_pedido_id_detalle_seq TO admin_rol;
GRANT SELECT,USAGE ON SEQUENCE public.detalle_pedido_id_detalle_seq TO mesero_rol;


GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.division_cuenta TO admin_rol;



GRANT SELECT,USAGE ON SEQUENCE public.division_cuenta_id_division_seq TO admin_rol;
GRANT SELECT,USAGE ON SEQUENCE public.division_cuenta_id_division_seq TO mesero_rol;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.empleado TO admin_rol;


GRANT SELECT,USAGE ON SEQUENCE public.empleado_id_empleado_seq TO admin_rol;
GRANT SELECT,USAGE ON SEQUENCE public.empleado_id_empleado_seq TO mesero_rol;


GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.mesa TO admin_rol;

GRANT SELECT,USAGE ON SEQUENCE public.mesa_id_mesa_seq TO admin_rol;
GRANT SELECT,USAGE ON SEQUENCE public.mesa_id_mesa_seq TO mesero_rol;



GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pago TO admin_rol;



GRANT SELECT,USAGE ON SEQUENCE public.pago_id_pago_seq TO admin_rol;
GRANT SELECT,USAGE ON SEQUENCE public.pago_id_pago_seq TO mesero_rol;



GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pedido TO admin_rol;
GRANT INSERT,UPDATE ON TABLE public.pedido TO mesero_rol;



GRANT SELECT,USAGE ON SEQUENCE public.pedido_id_pedido_seq TO admin_rol;
GRANT SELECT,USAGE ON SEQUENCE public.pedido_id_pedido_seq TO mesero_rol;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.producto TO admin_rol;
GRANT SELECT,UPDATE ON TABLE public.producto TO proveedor_rol;



GRANT SELECT,USAGE ON SEQUENCE public.producto_id_producto_seq TO admin_rol;
GRANT SELECT,USAGE ON SEQUENCE public.producto_id_producto_seq TO mesero_rol;



GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.v_cuenta_mesa TO admin_rol;
GRANT SELECT ON TABLE public.v_cuenta_mesa TO mesero_rol;


GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.v_detalle_cuenta_mesa TO admin_rol;
GRANT SELECT ON TABLE public.v_detalle_cuenta_mesa TO mesero_rol;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.v_detalle_pedido_completo TO admin_rol;
GRANT SELECT ON TABLE public.v_detalle_pedido_completo TO mesero_rol;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.v_detalle_pedido_mesero TO admin_rol;
GRANT SELECT ON TABLE public.v_detalle_pedido_mesero TO mesero_rol;


GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.v_division_cuenta_mesa TO admin_rol;
GRANT SELECT ON TABLE public.v_division_cuenta_mesa TO mesero_rol;



GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.v_ingresos_dia_semana TO admin_rol;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.v_pedidos_activos TO admin_rol;
GRANT SELECT ON TABLE public.v_pedidos_activos TO mesero_rol;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.v_pedidos_mesero TO admin_rol;
GRANT SELECT ON TABLE public.v_pedidos_mesero TO mesero_rol;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.v_pedidos_por_dia TO admin_rol;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.v_productos_disponibles TO admin_rol;
GRANT SELECT ON TABLE public.v_productos_disponibles TO proveedor_rol;


GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.v_productos_mas_vendidos TO admin_rol;
GRANT SELECT ON TABLE public.v_productos_mas_vendidos TO proveedor_rol;


