import { useEffect, useState } from "react";
import api from "../services/api";

export default function ListPage() {
 const [data, setData] = useState([]);
 const [loading, setLoading] = useState(true);
 const [search, setSearch] = useState("");

 const fetchData = () => {
  setLoading(true);
  api.get("/api/incidents/all")
   .then(res => setData(res.data.content))
   .catch(() => setData([]))
   .finally(() => setLoading(false));
 };

 useEffect(() => {
  fetchData();
 }, []);

 const handleSearch = (value) => {
  setSearch(value);

  if (!value) {
   fetchData();
   return;
  }

  api.get(`/api/incidents/search?q=${value}`)
   .then(res => setData(res.data))
   .catch(() => setData([]));
 };

 const handleDelete = (id) => {
  api.delete(`/api/incidents/${id}`)
   .then(() => {
    setData(prev => prev.filter(item => item.id !== id));
   });
 };

 if (loading) {
  return <div className="p-6 text-gray-600">Loading...</div>;
 }

 if (data.length === 0) {
  return (
   <div className="p-6">
    <input
     className="border p-2 mb-4 w-full"
     placeholder="Search..."
     value={search}
     onChange={(e) => handleSearch(e.target.value)}
    />
    <p className="text-gray-500">No incidents found</p>
   </div>
  );
 }

 return (
  <div className="p-6">
   <h1 className="text-2xl font-bold mb-4">Incidents</h1>

   <input
    className="border p-2 mb-4 w-full"
    placeholder="Search..."
    value={search}
    onChange={(e) => handleSearch(e.target.value)}
   />

   <table className="w-full border border-gray-300">
    <thead className="bg-gray-100">
     <tr>
      <th className="p-2 border">ID</th>
      <th className="p-2 border">Title</th>
      <th className="p-2 border">Severity</th>
      <th className="p-2 border">Status</th>
      <th className="p-2 border">Date</th>
      <th className="p-2 border">Actions</th>
     </tr>
    </thead>

    <tbody>
     {data.map(item => (
      <tr key={item.id}>
       <td className="p-2 border">{item.id}</td>
       <td className="p-2 border">{item.title}</td>
       <td className="p-2 border">{item.severity}</td>
       <td className="p-2 border">{item.status}</td>
       <td className="p-2 border">{item.incidentDate}</td>
       <td className="p-2 border">
        <button
         className="text-red-500"
         onClick={() => handleDelete(item.id)}
        >
         Delete
        </button>
       </td>
      </tr>
     ))}
    </tbody>
   </table>
  </div>
 );
}