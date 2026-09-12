# CARETRACE_FINAL
STRIVERS TEAM PROJECT
CARETRACE_FINAL – Local Setup Guide

  Below are the exact requirements and commands to run both the frontend and backend of the project on your machine.

  ---

  📦 Frontend (React + Vite)

  ┌──────────────────────────┬──────────────────────────────────────────────────────────┐
  │       Requirement        │                         Details                          │
  ├──────────────────────────┼──────────────────────────────────────────────────────────┤
  │ Node.js                  │ ≥ 18.x (Vite 8.3.0 works with Node 18+; LTS recommended) │
  ├──────────────────────────┼──────────────────────────────────────────────────────────┤
  │ Package manager          │ npm (comes with Node) or Yarn/pnpm if you prefer         │
  ├──────────────────────────┼──────────────────────────────────────────────────────────┤
  │ Install                  │ npm install (inside the frontend folder)                 │
  ├──────────────────────────┼──────────────────────────────────────────────────────────┤
  │ Run dev server           │ npm run dev (or vite) – starts Vite dev server with HMR  │
  ├──────────────────────────┼──────────────────────────────────────────────────────────┤
  │ Default URL              │ http://localhost:5173                                    │
  ├──────────────────────────┼──────────────────────────────────────────────────────────┤
  │ Build for production     │ npm run build → output in dist/                          │
  ├──────────────────────────┼──────────────────────────────────────────────────────────┤
  │ Preview production build │ npm run preview                                          │
  └──────────────────────────┴──────────────────────────────────────────────────────────┘

  Steps

  # 1️⃣ Go to the frontend directory
  cd D:\CARETRACE_FINAL\frontend

  # 2️⃣ Install dependencies
  npm install

  # 3️⃣ Start the development server
  npm run dev

  ▎ The frontend is a plain Vite‑React app; no extra environment variables are required for basic local development.

  ---

  ☕ Backend (Spring Boot + MongoDB)

  ┌───────────────┬─────────────────────────────────────────────────────────────────────────────────────────────────┐
  │  Requirement  │                                             Details                                             │
  ├───────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────┤
  │ Java JDK      │ 21 (declared in pom.xml → <java.version>21</java.version>)                                      │
  ├───────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────┤
  │ Build tool    │ Maven 3.8+ (the project includes the Maven Wrapper)                                             │
  ├───────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────┤
  │ MongoDB       │ A running MongoDB instance (local or remote). Connection details are supplied via environment   │
  │               │ variables (see below).                                                                          │
  ├───────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────┤
  │               │ The backend uses springboot4-dotenv, so a .env file in the project root is read automatically.  │
  │ Environment   │ Expected variables: <br>• MONGO_URI – MongoDB connection URI (e.g. mongodb://localhost:27017)   │
  │ variables     │ <br>• DATABASE_NAME – name of the database to use <br>• JWT_SECRET – secret used to sign JSON   │
  │               │ Web Tokens                                                                                      │
  ├───────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────┤
  │ Run           │ ./mvnw spring-boot:run (Windows: mvnw.cmd spring-boot:run) or mvn spring-boot:run if you have   │
  │               │ Maven installed globally                                                                        │
  ├───────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────┤
  │ Default URL   │ http://localhost:8080 (Spring Boot’s default port)                                              │
  └───────────────┴─────────────────────────────────────────────────────────────────────────────────────────────────┘

  Steps

  # 1️⃣ Ensure you have JDK 21 installed and JAVA_HOME set
  #    (you can check with `java -version`)

  # 2️⃣ (Optional) Start a local MongoDB instance
  #    e.g. with Docker: docker run -d -p 27017:27017 --name mongo mongo:latest
  #    or install MongoDB Community Edition and start the service.

  # 3️⃣ Create a .env file in the project root (D:\CARETRACE_FINAL\.env)
  #    Example content:
  #    MONGO_URI=mongodb://localhost:27017
  #    DATABASE_NAME=caretrace
  #    JWT_SECRET=your‑super‑secret‑key‑change‑in‑prod

  # 4️⃣ Go to the project root
  cd D:\CARETRACE_FINAL

  # 5️⃣ Build and run with the Maven Wrapper
  ./mvnw spring-boot:run   # Windows: mvnw.cmd spring-boot:run

  ▎ If you prefer not to use the wrapper, you can run mvn spring-boot:run after installing Maven globally.

  ---

  📌 Quick Checklist

  1. Frontend
     - Node ≥ 18 installed
     - npm install → npm run dev
  2. Backend
     - JDK 21 installed
     - MongoDB running (or accessible)
     - .env file with MONGO_URI, DATABASE_NAME, JWT_SECRET
     - ./mvnw spring-boot:run

  Once both processes are running, you can interact with the full stack: the frontend will consume the backend APIs
  (typically on http://localhost:8080/api… – adjust any proxy settings in vite.config.js if you need the frontend to
  call the backend on a different port or host).

  ---

  📝 Notes

  - The provided README.md files are minimal; the above details are derived from package.json, pom.xml, and
    application.properties.
  - No additional build steps (e.g., code generation) are required.
  - If you encounter port conflicts, change the Vite port (vite.config.js) or the Spring Boot port (server.port in
    application.properties or via SERVER_PORT environment variable).

  You should now be able to develop and test the application locally. Happy coding! 🚀
