import React from "react";
import { Routes, Route } from "react-router-dom";

import Login from "./login/Login";
import RouteProtector from "./login/components/RouteProtector";
import AuthProvider from "./login/components/AuthProvider";
import DashboardLayout from "./common/components/DashboardLayout";

function App() {
  return (
    <>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route
            path="/"
            element={
              <RouteProtector>
                <DashboardLayout />
              </RouteProtector>
            }
          >
            <Route path="dashboard" element={<h1>Test</h1>} />
            <Route path="*" element={<h1>404 Not found</h1>} />
          </Route>
        </Routes>
      </AuthProvider>
    </>
  );
}

export default App;
