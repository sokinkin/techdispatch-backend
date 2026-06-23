# TechDispatch — Backend API Endpoints

**Base URL:** `http://localhost:8080`

Interactive docs (while the backend is running):
- **Swagger UI:** http://localhost:8080/api-ui
- **Raw OpenAPI JSON:** http://localhost:8080/api

---

## Auth — `/auth`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/auth/register` | Register a new account (returns JWT) |
| POST | `/auth/login` | Log in (returns JWT) |

## Account — `/account`
| Method | Path | Description |
|--------|------|-------------|
| PUT | `/account/password` | Change password (204 No Content) |

## Customers — `/customer`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/customer/all` | List all customers |
| GET | `/customer?id={id}` | Get one customer by id |
| POST | `/customer` | Add a customer |
| PUT | `/customer?id={id}` | Update a customer |

## Technicians — `/technician`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/technician/all` | List all technicians |
| GET | `/technician?id={id}` | Get one technician by id |
| POST | `/technician` | Add a technician |
| PUT | `/technician?id={id}` | Update a technician |

## Jobs — `/job`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/job/all` | List all jobs |
| GET | `/job?id={id}` | Get one job by id |
| POST | `/job` | Add a job |

## Visits — `/visit`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/visit/all` | List all visits |
| GET | `/visit?id={id}` | Get one visit by id |
| POST | `/visit` | Add a visit |
| PUT | `/visit?id={id}` | Update a visit's details (location, technician, date, description, services, cost) |
| GET | `/visit/by-customer?customerId={id}` | Visits for a customer |
| GET | `/visit/by-technician?technicianId={id}` | Visits for a technician |
| PUT | `/visit/status?id={id}&status={status}` | Update visit status |
| PUT | `/visit/reschedule?id={id}&date={date}` | Reschedule a visit |

## Locations — `/location`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/location/all` | List all locations |
| GET | `/location?id={id}` | Get one location by id |
| POST | `/location` | Add a location |
| GET | `/location/by-customer?customerId={id}` | Locations for a customer |
| POST | `/location/for-customer?customerId={id}` | Add a location for a customer |

## Availability — `/availability`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/availability/by-technician?technicianId={id}` | Availability slots for a technician |
| POST | `/availability/for-technician?technicianId={id}` | Add availability for a technician |
| DELETE | `/availability?id={id}` | Delete an availability slot |

---

## Seeded test logins
All seeded logins use the password **`password123`**.

| Role | Email |
|------|-------|
| Technician | `nikos@techdispatch.gr` |
| Customer | `maria@acme.gr` |
