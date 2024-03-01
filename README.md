# CS157C-Team9
Using NoSQL Database Cassandra

## Project Setup
Use this to sync dependencies n stuff
1. open terminal in the project folder
2. In the root folder, type `npx create-next-app@latest`
3. Type `y`
4. For name, use `web-server`
5. TypeScript: Yes, ESLint: Yes, Tailwind CSS: No, src/: Yes, App Router: Yes, import alias: No
6. Wait for installer to finish. You have just finished installing the web server
7. Create directory for `app-server` if it doesn't exist already
8. cd into it
9. type `npx express-generator`
10. You have just finished installing Express.js

## Starting Servers
It is important to note this project uses two servers. The react.js one is for the web server that displays front end, while express.js is used for the back end
1. Navigate to `app-server` in terminal
2. Type `node .\bin\www`
3. Note that I have modified the default port of the server to 4000 because react's default is 3000
4. If you see an error asking you to install `http-errors`, type `npm install http-errors` and try again
5. If the terminal halts, everything should be working, navigate to `localhost:4000` to see a test page
6. In a new terminal instance, navigate to `web-server`
7. type `npm run dev`
8. The server should give you a confirmation that it is up, check it out on `localhost:3000`