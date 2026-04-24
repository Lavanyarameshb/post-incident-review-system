import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import ListPage from "./pages/ListPage";
import CreateEdit from "./pages/CreateEdit";
import ProtectedRoute from "./components/ProtectedRoute";
import { AuthProvider } from "./context/AuthContext";

export default function App() {
 return (
  <AuthProvider>
   <BrowserRouter>
    <Routes>
     <Route path="/login" element={<Login />} />

     <Route
      path="/"
      element={
       <ProtectedRoute>
        <div className="p-6 space-y-6">
         <CreateEdit />
         <ListPage />
        </div>
       </ProtectedRoute>
      }
     />

     {/* fallback */}
     <Route path="*" element={<Navigate to="/login" />} />
    </Routes>
   </BrowserRouter>
  </AuthProvider>
 );
}