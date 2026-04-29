import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
 const [user, setUser] = useState(null);

 useEffect(() => {
  const saved = localStorage.getItem("user");
  if (saved) setUser(JSON.parse(saved));
 }, []);

 const login = (username, password) => {
  if (username === "admin" && password === "admin") {
   const u = { username };
   setUser(u);
   localStorage.setItem("user", JSON.stringify(u));
   return true;
  }
  return false;
 };

 const logout = () => {
  setUser(null);
  localStorage.removeItem("user");
 };

 return (
  <AuthContext.Provider value={{ user, login, logout }}>
   {children}
  </AuthContext.Provider>
 );
};

export const useAuth = () => useContext(AuthContext);