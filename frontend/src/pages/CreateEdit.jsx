import { useState } from "react";
import api from "../services/api";

export default function CreateEdit() {
 const [form, setForm] = useState({
  title: "",
  description: "",
  severity: "",
  status: ""
 });
 const [error, setError] = useState("");

 const handleChange = (e) => {
  setForm({ ...form, [e.target.name]: e.target.value });
 };

 const handleSubmit = async (e) => {
  e.preventDefault();

  if (!form.title || !form.severity) {
   setError("Title and Severity are required");
   return;
  }

  try {
   await api.post("/api/incidents", form);
   alert("Saved successfully");
  } catch {
   setError("Error saving data");
  }
 };

 return (
  <div className="min-h-screen bg-gray-100 flex items-center justify-center">
   <div className="bg-white p-8 rounded-xl shadow-md w-full max-w-xl">
    
    <h1 className="text-2xl font-bold mb-6 text-gray-800">
     Create Incident
    </h1>

    {error && (
     <p className="text-red-500 mb-4">{error}</p>
    )}

    <form onSubmit={handleSubmit} className="space-y-4">

     <input
      name="title"
      placeholder="Title"
      onChange={handleChange}
      className="w-full border border-gray-300 p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
     />

     <textarea
      name="description"
      placeholder="Description"
      onChange={handleChange}
      className="w-full border border-gray-300 p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
     />

     <select
      name="severity"
      onChange={handleChange}
      className="w-full border border-gray-300 p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
     >
      <option value="">Select Severity</option>
      <option value="LOW">LOW</option>
      <option value="MEDIUM">MEDIUM</option>
      <option value="HIGH">HIGH</option>
      <option value="CRITICAL">CRITICAL</option>
     </select>

     <select
      name="status"
      onChange={handleChange}
      className="w-full border border-gray-300 p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
     >
      <option value="">Select Status</option>
      <option value="OPEN">OPEN</option>
      <option value="IN_PROGRESS">IN_PROGRESS</option>
      <option value="RESOLVED">RESOLVED</option>
      <option value="CLOSED">CLOSED</option>
     </select>

     <button
      type="submit"
      className="w-full bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700 transition"
     >
      Save Incident
     </button>

    </form>
   </div>
  </div>
 );
}