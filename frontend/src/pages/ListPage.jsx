import { useEffect, useState } from "react";
import api from "../services/api";

export default function ListPage() {
 const [data, setData] = useState([]);
 const [loading, setLoading] = useState(true);

 useEffect(() => {
  api.get("/all")
   .then(res => setData(res.data))
   .catch(() => setData([]))
   .finally(() => setLoading(false));
 }, []);

 if (loading) {
  return <div className="p-6 text-gray-600">Loading...</div>;
 }

 if (data.length === 0) {
  return <div className="p-6 text-gray-500">No incidents found</div>;
 }

 return (
  <div className="p-6">
   <h1 className="text-2xl font-bold mb-4">Incidents</h1>

   <table className="w-full border border-gray-300">
    <thead className="bg-gray-100">
     <tr>
      <th className="p-2 border">ID</th>
      <th className="p-2 border">Title</th>
      <th className="p-2 border">Severity</th>
      <th className="p-2 border">Status</th>
      <th className="p-2 border">Date</th>
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
      </tr>
     ))}
    </tbody>
   </table>
  </div>
 );
}