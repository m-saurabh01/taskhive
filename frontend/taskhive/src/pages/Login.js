import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login } from '../services/authService';
import './LoginStyle.css';
import Navbar from './Navbar';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const history = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await login({ email, password });
            history('/dashboard'); // Redirect to dashboard after successful login
        } catch (error) {
            setError('Invalid email or password');
        }
    };

    return (
        <div className='login-container'>
            <Navbar />
            <form className='login_form' onSubmit={handleSubmit}>
                <h2 className='login_heading'>Sign In</h2>{' '}
                {/* Added Sign In heading */}
                <div className='input_box'>
                    <label htmlFor='email'>Email</label>
                    <input
                        type='email'
                        id='email'
                        placeholder='Enter email address'
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className='input_box'>
                    <div className='password_title'>
                        <label htmlFor='password'>Password</label>
                    </div>
                    <input
                        type='password'
                        id='password'
                        placeholder='Enter your password'
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {error && (
                    <p className='err-msg' style={{ color: 'red' }}>
                        {error}
                    </p>
                )}
                <button type='submit'>Log In</button>
                <p className='sign_up'>
                    Don't have an account? <a href='/register'>Sign up</a>
                </p>
            </form>
        </div>
    );

};

export default Login;
