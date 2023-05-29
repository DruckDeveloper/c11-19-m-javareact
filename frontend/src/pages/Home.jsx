// import styled from 'styled-components'
import { Layout } from '../routes/Layout'
import { Hero } from '../components/hero/Hero'
import Pillars from '../components/Pillars'
import styled from 'styled-components'

const MainStyled = styled.main`
  display: grid;
  gap: 0.5rem;
`

function Home() {
  return (
    <Layout>
      <MainStyled>
        <Hero />
        <Pillars />
      </MainStyled>
    </Layout>
  )
}

export default Home
