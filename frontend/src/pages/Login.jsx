import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default function Login() {
 const { login } = useAuth();
 const navigate = useNavigate();

 const [form, setForm] = useState({ username: "", password: "" });
 const [error, setError] = useState("");

 const handleSubmit = (e) => {
  e.preventDefault();
  const ok = login(form.username, form.password);
  if (!ok) setError("Invalid credentials");
  else {
   setError("");
   navigate("/");
  }
 };

 return (
  <div className="min-h-screen flex items-center justify-center bg-gray-100">
   <div className="bg-white p-8 rounded-xl shadow-md w-80">
    <h1 className="text-xl font-bold mb-4">Login</h1>
    {error && <p className="text-red-500 mb-3">{error}</p>}

    <form onSubmit={handleSubmit} className="space-y-3">
     <input
      placeholder="Username"
      className="border p-2 w-full"
      onChange={(e) => setForm({ ...form, username: e.target.value })}
     />
     <input
      type="password"
      placeholder="Password"
      className="border p-2 w-full"
      onChange={(e) => setForm({ ...form, password: e.target.value })}
     />
     <button className="bg-blue-600 text-white w-full py-2 rounded">
      Login
     </button>
    </form>
   </div>
  </div>
 );
}