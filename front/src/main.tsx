import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import './index.css'
import "@radix-ui/themes/styles.css";
import App from '@/App'
import { Theme } from '@radix-ui/themes';
import { AuthProvider } from '@/auth';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Theme accentColor="blue" radius="medium">
        <AuthProvider>
          <App />
        </AuthProvider>
      </Theme>
    </BrowserRouter>
  </StrictMode>,
)
