import { createContext, useState } from "react";

interface AuthContextType {
  user: object | null;
  login: (user: object) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: React.ReactNode;
}

const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [user, setUser] = useState<object | null>(null);

  const login = (user: object) => {
    setUser(user);
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem("jwt");
  };

  const authContextValue: AuthContextType = {
    user,
    login,
    logout,
  };

  return (
    <AuthContext.Provider value={authContextValue}>
      {children}
    </AuthContext.Provider>
  );
};

export { AuthProvider, AuthContext };
